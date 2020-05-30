package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.LocalizacaoListener;
import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.data.LocationObservedData;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ListenerActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.PermissionActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.dupla.SelecionarParceiroActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.IntentUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


public class AtividadeActivity extends AppCompatActivity implements PermissionActivity, ListenerActivity, Observer {

    private static final int CODE_SELECIONAR_PARCEIRO = 100;

    @Getter
    @Setter(AccessLevel.PRIVATE)
    protected AtividadeEstado atividadeEstado = null;

    private AtividadeController atividadeController;
    private LocationManager locationManager;
    private LocalizacaoListener locationListener;

    private final List<String> permissoes = new ArrayList() {{
        add(Manifest.permission.ACCESS_FINE_LOCATION);
        add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_atividade);
        grantPermissions();
        AtividadeTipo tipoSelecionado = AtividadeTipo.fromIntent(getIntent());
        if (AtividadeTipo.SOZINHO.equals(tipoSelecionado)) {
            initListeners();
            iniciarContagemRegressiva();
        } else {
            new IntentUtils().startActivityForResult(this, SelecionarParceiroActivity.class, CODE_SELECIONAR_PARCEIRO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_SELECIONAR_PARCEIRO && resultCode == RESULT_OK) {
            initListeners();
            iniciarContagemRegressiva();
        }
    }

    @Override
    public void finish() {
        if (locationListener != null) {
            locationListener.deleteObservers();
            if (locationManager != null) {
                locationManager.removeUpdates(locationListener);
            }
        }
        locationListener = null;
        locationManager = null;

        super.finish();
    }

    @Override
    public void onBackPressed() {
        if (getAtividadeEstado() == null) {
            finish();
        }
    }

    @Override
    public void grantPermissions() {
        Dexter.withActivity(this)
                .withPermissions(permissoes)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (!report.areAllPermissionsGranted()) {
                            Toast.makeText(AtividadeActivity.this, "Verifique as permissões dadas ao app!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void initListeners() {
        // Localização
        if (permissoes.stream().anyMatch(p -> checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED)) {
            // Todo: AlertDialog
            finish();
        } else {
            locationListener = new LocalizacaoListener(this);
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.removeUpdates(locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4500, 10, locationListener);
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof LocationObservedData) {
            LocationObservedData data = (LocationObservedData) arg;
            if (data.getMetodo() == LocationObservedData.Metodo.LOCATION_CHANGED) {
                if (getAtividadeEstado().equals(AtividadeEstado.EM_ANDAMENTO)) {
                    AtividadeFragment atividadeFragment = (AtividadeFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_atividade);
                    Atividade atividade = atividadeController.atualizarAtividade(data);
                    atividadeFragment.atualizar(atividade);
                }
            } else if (data.getMetodo() == LocationObservedData.Metodo.PROVIDER_DISABLED) {
                AtividadeFragment atividadeFragment = (AtividadeFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_atividade);
                atividadeFragment.pausarAtividade();
            }
        }
    }

    private void iniciarContagemRegressiva() {
        FragmentUtils fragmentUtils = new FragmentUtils();
        fragmentUtils.loadFragment(this, R.id.fl_container_atividade, new ContagemRegressivaFragment());
    }

    protected void iniciarAtividade() {
        setAtividadeEstado(AtividadeEstado.EM_ANDAMENTO);
        FragmentUtils fragmentUtils = new FragmentUtils();
        ContagemRegressivaFragment contagemRegressivaFragment = (ContagemRegressivaFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_atividade);
        assert contagemRegressivaFragment != null;
        fragmentUtils.kill(contagemRegressivaFragment);

        atividadeController = new AtividadeController();
        fragmentUtils.loadFragment(this, R.id.fl_container_atividade, new AtividadeFragment());
    }


    protected void retomarAtividade() {
        setAtividadeEstado(AtividadeEstado.EM_ANDAMENTO);
    }

    protected void pausarAtividade() {
        setAtividadeEstado(AtividadeEstado.PAUSADA);
    }

    protected void finalizarAtividade(long termino, long duracaoMillis) {
        setAtividadeEstado(AtividadeEstado.FINALIZADA);
        Atividade atividade = atividadeController.finalizar(termino, duracaoMillis);
        try {
            atividadeController.salvar(atividade, getApplicationContext(),
                    this::finish,
                    () -> Toast.makeText(this, "ERROU", Toast.LENGTH_SHORT).show()
            );
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
