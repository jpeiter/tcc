package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.atividade;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

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
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.ListenerActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.PermissionActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeController;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.enums.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.data.LocationObservedData;
import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.LocalizacaoListener;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


public class AtividadeActivity extends AppCompatActivity implements PermissionActivity, ListenerActivity, Observer {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    protected AtividadeEstado atividadeEstado = AtividadeEstado.PAUSADA;

    private LocationManager locationManager;

    private LocalizacaoListener locationListener;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PROTECTED)
    private AtividadeController atividadeController;

    private final List<String> permissoes = new ArrayList() {{
        add(Manifest.permission.ACCESS_FINE_LOCATION);
        add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);
        grantPermissions();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void grantPermissions() {
        Dexter.withActivity(this)
                .withPermissions(permissoes)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            initListeners();
                            iniciarContagemRegressiva();
                        } else {
                            finish();
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
        if (permissoes.stream().allMatch(p -> checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED)) {
            finish();
        }
        locationListener = new LocalizacaoListener(this);
        atividadeController = new AtividadeController();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4500, 10, locationListener);
    }

    private void iniciarContagemRegressiva() {
        FragmentUtils fragmentUtils = new FragmentUtils();
        fragmentUtils.loadFragment(this, R.id.fl_container_atividade, new ContagemRegressivaFragment());
    }

    protected void iniciarAtividade() {
        FragmentUtils fragmentUtils = new FragmentUtils();
        ContagemRegressivaFragment contagemRegressivaFragment = (ContagemRegressivaFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_atividade);
        assert contagemRegressivaFragment != null;
        fragmentUtils.kill(contagemRegressivaFragment);

        fragmentUtils.loadFragment(this, R.id.fl_container_atividade, new AtividadeFragment());
        setAtividadeEstado(AtividadeEstado.EM_ANDAMENTO);
    }


    protected void retomarAtividade() {
        setAtividadeEstado(AtividadeEstado.EM_ANDAMENTO);
    }

    protected void pausarAtividade() {
        setAtividadeEstado(AtividadeEstado.PAUSADA);
    }

    protected void finalizarAtividade() {
        finish();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof LocationObservedData) {
            LocationObservedData data = (LocationObservedData) arg;
            if (data.getMetodo() == LocationObservedData.Metodo.LOCATION_CHANGED) {
                if (getAtividadeEstado().equals(AtividadeEstado.EM_ANDAMENTO)) {
                    Atividade atividade = atividadeController.atualizarAtividade(data);
                    AtividadeFragment atividadeFragment = (AtividadeFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_atividade);
                    atividadeFragment.atualizar(atividade);
                }
            }
        }
    }
}
