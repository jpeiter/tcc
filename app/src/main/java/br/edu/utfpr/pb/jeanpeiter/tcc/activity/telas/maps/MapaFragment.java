package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.maps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Observable;
import java.util.Observer;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.GenericActivity;

public class MapaFragment extends Fragment implements OnMapReadyCallback, Observer {

    private GoogleMap gmap;
    private View parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_mapa, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return parent;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        LatLng utfprPato = new LatLng(-26.197575, -52.688968);
        gmap.addMarker(new MarkerOptions().position(utfprPato).title("UTFPR - Pato Branco"));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(utfprPato, 18));
    }

    @Override
    public void update(Observable o, Object arg) {
        LocationObservedData data = (LocationObservedData) arg;
        LatLng sydney = null;
        if (data.getLocation() != null) {
            new LatLng(data.getLocation().getLatitude(), data.getLocation().getLongitude());
        } else {
            sydney = new LatLng(-34, 151);
        }

        gmap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        switch (data.getMetodo()){
//            case LOCATION_CHANGED:
//        }
    }
}
