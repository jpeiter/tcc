package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.atividade;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

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
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.PermissionActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.location.LocationListenerController;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.data.LocationObservedData;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeActivity extends AppCompatActivity implements PermissionActivity, Observer {

    protected AtividadeEstado atividadeEstado = AtividadeEstado.PAUSADA;

    private LocationListenerController locationListener;
    private LocationManager locationManager;

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
        AtividadeFragment fragment = (AtividadeFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_atividade);
        fragment.getBtnPausarParar().performClick();
        if (getAtividadeEstado() == AtividadeEstado.PAUSADA) {
            super.onBackPressed();
        }
    }

    @Override
    public void grantPermissions() {
        Dexter.withActivity(this)
                .withPermissions(permissoes)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            iniciarListener();
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

    private void iniciarListener() {
        if (permissoes.stream().allMatch(p -> checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED)) {
            finish();
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(true);

        locationListener = new LocationListenerController(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(provider, 5000, 5, locationListener);

        new FragmentUtils().loadFragment(this, R.id.fl_container_atividade, new ContagemRegressivaFragment());
    }

    void iniciarAtividade() {
        FragmentUtils fragmentUtils = new FragmentUtils();
        ContagemRegressivaFragment fragment = (ContagemRegressivaFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_atividade);

        fragmentUtils.kill(fragment);
        fragmentUtils.loadFragment(this, R.id.fl_container_atividade, new AtividadeFragment());
        setAtividadeEstado(AtividadeEstado.EM_ANDAMENTO);
    }


    public void retomarAtividade() {
        setAtividadeEstado(AtividadeEstado.EM_ANDAMENTO);
    }

    public void pausarAtividade() {
        setAtividadeEstado(AtividadeEstado.PAUSADA);
    }

    public void finalizarAtividade() {
        finish();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof LocationObservedData){
            Toast.makeText(this, "NA ACTIVITY!!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
