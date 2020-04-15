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

import br.edu.utfpr.pb.jeanpeiter.tcc.R;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

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
}
