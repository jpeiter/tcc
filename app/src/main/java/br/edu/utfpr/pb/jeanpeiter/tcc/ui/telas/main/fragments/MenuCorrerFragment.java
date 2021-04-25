package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.connectivity.info.NetworkInformation;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.LocalizacaoListener;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.PermissionActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.AtividadeActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.dupla.SelecionarParceiroActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.maps.MapaFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.IntentUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class MenuCorrerFragment extends Fragment implements GenericActivity, PermissionActivity {

    FrameLayout flMapCorrer;
    FloatingActionButton btnIniciarDupla;
    FloatingActionButton btnIniciarSozinho;
    LocalizacaoListener locationListener;

    private View parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_menu_correr, container, false);

        initViews();
        loadFragment(new MapaFragment());
        grantPermissions();
        return parent;
    }

    @Override
    public void initViews() {
        setFlMapCorrer(parent.findViewById(R.id.flMapCorrer));
        setBtnIniciarDupla(parent.findViewById(R.id.btnIniciarDupla));
        setBtnIniciarSozinho(parent.findViewById(R.id.btnIniciarSozinho));
        getBtnIniciarSozinho().setOnClickListener(clickListener);
        getBtnIniciarDupla().setOnClickListener(clickListener);

        getBtnIniciarDupla().setVisibility(NetworkInformation.isNetworkAvailable(getContext()) ? View.VISIBLE : View.INVISIBLE);
    }

    private void loadFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.flMapCorrer, fragment)
                .addToBackStack(null)
                .commit();
    }

    private View.OnClickListener clickListener = v -> {
        AtividadeTipo tipo = v.getId() == R.id.btnIniciarDupla ? AtividadeTipo.DUPLA : AtividadeTipo.SOZINHO;
        Intent i = new Intent(getContext(), AtividadeTipo.SOZINHO.equals(tipo) ?
                AtividadeActivity.class :
                SelecionarParceiroActivity.class
        );
        tipo.toIntent(i);
        startActivityForResult(i, 1);
    };


    @Override
    public void grantPermissions() {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {

                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getContext(), "PEMISSAO NEGADA", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


}
