package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.connectivity.info.GpsInformation;
import br.edu.utfpr.pb.jeanpeiter.tcc.connectivity.info.NetworkInformation;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeController;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeEstadoSingleton;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseAtividadeDuplaController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.LocalizacaoListener;
import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.data.LocationObservedData;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ListenerActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.PermissionActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.dupla.SelecionarParceiroActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.fragments.AtividadeFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.fragments.ContagemRegressivaFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.modelo.AtividadeActivityBundle;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.modelo.AtividadeFragmentBundle;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.DialogUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.modelo.AtividadeFragmentBundle.AtividadeFragmentMetodo.ATUALIZAR;
import static br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.modelo.AtividadeFragmentBundle.AtividadeFragmentMetodo.PAUSAR;
import static br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.modelo.AtividadeFragmentBundle.AtividadeFragmentMetodo.RETOMAR;


public class AtividadeActivity extends AppCompatActivity implements PermissionActivity, ListenerActivity {

    private final UUID atividadeId = UUID.randomUUID();

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private AtividadeTipo tipoSelecionado = null;

    private AtividadeController atividadeController;
    private LocationManager locationManager;
    private LocalizacaoListener locationListener;

    private final List<String> permissoes = new ArrayList() {{
        add(Manifest.permission.ACCESS_FINE_LOCATION);
        add(Build.VERSION.SDK_INT == Build.VERSION_CODES.Q ?
                Manifest.permission.ACCESS_BACKGROUND_LOCATION :
                Manifest.permission.ACCESS_COARSE_LOCATION
        );
    }};

    private boolean isAtividadeDupla() {
        return AtividadeTipo.DUPLA.equals(getTipoSelecionado());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        grantPermissions();
        setTipoSelecionado(AtividadeTipo.fromIntent(getIntent()));

        if (isAtividadeDupla()) {
            FirebaseDatabase.getInstance().goOnline();
            String parceiroUid = this.getIntent().getStringExtra(SelecionarParceiroActivity.PARCEIRO_UID_EXTRA);
            FirebaseAtividadeDuplaController.getInstance().zerarPendencias();
            iniciarAtividadeDupla(parceiroUid);
            monitorarAtividadeParceiro();
        }

        initListeners();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void finish() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (locationListener != null && locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
        locationListener = null;
        locationManager = null;
        super.finish();
    }

    @Override
    protected void onDestroy() {
        AtividadeEstadoSingleton.getInstance().destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (AtividadeEstadoSingleton.getInstance().getEstado() == null) {
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
        if (!GpsInformation.isLocationEnabled(this)) {
            new DialogUtils().build(this, "Ser")
                    .setNegativeButton(getString(R.string.agora_nao), (dialog, which) -> AtividadeActivity.this.finish())
                    .setPositiveButton(getString(R.string.confirmar), (dialog, which) -> startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .create().show();
        } else if (permissoes.stream().anyMatch(p -> checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED)) {
            // Todo: AlertDialog
            finish();
        } else {
            locationListener = new LocalizacaoListener();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, locationListener);
            iniciarContagemRegressiva();
        }
    }

    private void iniciarContagemRegressiva() {
        FragmentUtils fragmentUtils = new FragmentUtils();
        fragmentUtils.loadFragment(this, R.id.fl_container_atividade, new ContagemRegressivaFragment());
    }

    private void iniciarAtividade() {
        if (AtividadeEstadoSingleton.getInstance().getEstado() == null) {
            AtividadeEstadoSingleton.getInstance().setEstado(AtividadeEstado.EM_ANDAMENTO);
            FragmentUtils fragmentUtils = new FragmentUtils();
            ContagemRegressivaFragment contagemRegressivaFragment = (ContagemRegressivaFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_atividade);
            assert contagemRegressivaFragment != null;
            fragmentUtils.kill(contagemRegressivaFragment);

            atividadeController = new AtividadeController(atividadeId, getTipoSelecionado(), this.getBaseContext());
            fragmentUtils.loadFragment(this, R.id.fl_container_atividade, new AtividadeFragment());
        }
    }

    private void iniciarAtividadeDupla(String parceiroUid) {
        if (NetworkInformation.isNetworkAvailable(this)) {
            FirebaseAtividadeDuplaController.getInstance().iniciar(parceiroUid, atividadeId.toString());
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void updateLocation(LocationObservedData data) {
        switch (data.getMetodo()) {
            case LOCATION_CHANGED:
                if (AtividadeEstadoSingleton.getInstance().isEmAndamento()) {
                    Atividade atividade = atividadeController.atualizar(data);
                    this.updateFragment(new AtividadeFragmentBundle(ATUALIZAR, atividade));

                    if (isAtividadeDupla()) {
                        FirebaseAtividadeDuplaController.getInstance().atualizar(atividade);
                    }

                    updateMapa(data.getLocation());
                }
                break;
            case PROVIDER_DISABLED:
                this.updateFragment(new AtividadeFragmentBundle(PAUSAR));
                break;
            case PROVIDER_ENABLED:
                this.updateFragment(new AtividadeFragmentBundle(RETOMAR));
                break;
        }
    }

    private void monitorarAtividadeParceiro() {
        FirebaseAtividadeDuplaController.getInstance().monitorarParceiro(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AtividadeDTO dto = dataSnapshot.getValue(AtividadeDTO.class);
                if (dto != null) {
                    Atividade atividade = new Atividade().parse(dto);
                    switch (atividade.getEstado()) {
                        case EM_ANDAMENTO:
                            if (!AtividadeEstadoSingleton.getInstance().isEmAndamento()) {
                                AtividadeActivity.this.updateFragment(new AtividadeFragmentBundle(RETOMAR));
                            }
                            break;
                        case PAUSADA:
                            if (!AtividadeEstadoSingleton.getInstance().isPausada()) {
                                AtividadeActivity.this.updateFragment(new AtividadeFragmentBundle(PAUSAR));
                            }
                        case FINALIZADA:
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(AtividadeActivityBundle bundle) {
        switch (bundle.getMetodo()) {
            case INICIAR:
                this.iniciarAtividade();
                break;
            case PAUSAR:
                this.pausarAtividade();
                break;
            case RETOMAR:
                this.retomarAtividade();
                break;
            case FINALIZAR:
                this.finalizarAtividade(bundle.getTermino(), bundle.getTempoDecorrido());
                break;
        }
    }

    private void pausarAtividade() {
        AtividadeEstadoSingleton.getInstance().setEstado(AtividadeEstado.PAUSADA);
        Atividade atividade = atividadeController.mudarEstado(AtividadeEstado.PAUSADA);
        if (isAtividadeDupla()) {
            FirebaseAtividadeDuplaController.getInstance().atualizar(atividade);
        }
    }

    private void retomarAtividade() {
        AtividadeEstadoSingleton.getInstance().setEstado(AtividadeEstado.EM_ANDAMENTO);
        Atividade atividade = atividadeController.mudarEstado(AtividadeEstado.EM_ANDAMENTO);
        if (isAtividadeDupla()) {
            FirebaseAtividadeDuplaController.getInstance().atualizar(atividade);
        }
    }

    private void finalizarAtividade(long termino, long duracaoMillis) {
        AtividadeEstadoSingleton.getInstance().setEstado(AtividadeEstado.FINALIZADA);
        Atividade atividade = atividadeController.finalizar(termino, duracaoMillis);
        if (isAtividadeDupla()) {
            FirebaseAtividadeDuplaController.getInstance().finalizar(atividade);
        }
        try {
            atividadeController.salvar(atividade, getApplicationContext(),
                    this::finish,
                    () -> {
                        AtividadeEstadoSingleton.getInstance().setEstado(AtividadeEstado.PAUSADA);
                        atividadeController.mudarEstado(AtividadeEstado.PAUSADA);
                    }
            );
        } catch (Exception e) {
            AtividadeEstadoSingleton.getInstance().setEstado(AtividadeEstado.PAUSADA);
            atividadeController.mudarEstado(AtividadeEstado.PAUSADA);
        }
    }

    private void updateFragment(AtividadeFragmentBundle bundle) {
        EventBus.getDefault().post(bundle);
    }

    private void updateMapa(Location location) {
        EventBus.getDefault().post(new AtividadePosicao(location));
    }

}
