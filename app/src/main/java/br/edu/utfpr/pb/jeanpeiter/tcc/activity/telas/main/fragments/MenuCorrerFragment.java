package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.main.fragments;

import android.content.ComponentName;
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

import java.util.Observable;
import java.util.Observer;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.maps.LocationObservedData;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.maps.LocationService;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.maps.MapaFragment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class MenuCorrerFragment extends Fragment implements GenericActivity, Observer {

    FrameLayout flMapCorrer;
    FloatingActionButton btnIniciarDupla;
    FloatingActionButton btnIniciarSozinho;

    private View parent;

    private LocationService locationService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parent = inflater.inflate(R.layout.fragment_menu_correr, container, false);
        initViews();

        loadFragment(new MapaFragment());

        Intent service = new Intent(parent.getContext(), LocationService.class);

        locationService = new LocationService();
        locationService.addObserver(this);
        return parent;
    }

    @Override
    public void initViews() {
        setFlMapCorrer(parent.findViewById(R.id.flMapCorrer));

        setBtnIniciarDupla(parent.findViewById(R.id.btnIniciarDupla));
        setBtnIniciarSozinho(parent.findViewById(R.id.btnIniciarSozinho));
        getBtnIniciarSozinho().setOnClickListener(listener);
        getBtnIniciarDupla().setOnClickListener(listener);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flMapCorrer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private View.OnClickListener listener = v -> {
        String tipo = v.getId() == R.id.btnIniciarDupla ? "DUPLA" : "SOZINHO";
        Toast.makeText(v.getContext(), tipo, Toast.LENGTH_SHORT).show();
    };

    @Override
    public void update(Observable o, Object arg) {
        LocationObservedData data = (LocationObservedData) arg;

//        switch (data.getMetodo()){
//            case LOCATION_CHANGED:
//        }
    }
}
