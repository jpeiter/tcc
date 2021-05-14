package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.maps;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.LocationUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.ResourcesUtils;

public class StaticMapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gmap;
    private LocationUtils locationUtils;
    private PolylineOptions polylineOptions;
    private LatLngBounds latLngBounds;

    private List<LatLng> latLngs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_mapa, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        polylineOptions = new PolylineOptions()
                .color(R.color.secundaria)
                .width(18)
                .endCap(new RoundCap())
                .startCap(new RoundCap())
                .jointType(JointType.ROUND);
        locationUtils = new LocationUtils();

        return parent;
    }

    @Override
    public void onStop() {
        if (gmap != null) {
            gmap.clear();
        }
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMyLocationEnabled(true);
        EventBus.getDefault().post(Boolean.TRUE);
        gmap.setOnMyLocationButtonClickListener(this::zoomIn);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPosicoes(List<AtividadePosicao> posicoes) {
        gmap.clear();
        latLngs = new ArrayList<>();
        posicoes.forEach(this::addPosicao);
        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
            paintMap();
            zoomIn();
        });
    }

    private void addPosicao(AtividadePosicao posicao) {
        LatLng latLng = locationUtils.toLatLng(posicao);
        latLngs.add(latLng);
        latLngBounds = latLngBounds == null ?
                new LatLngBounds.Builder().include(latLng).build() :
                latLngBounds.including(latLng);
    }

    private void paintMap() {
        polylineOptions.addAll(latLngs);
        gmap.addMarker(getMarker(latLngs.get(0), R.drawable.ic_flag_green).title(getString(R.string.inicio)));
        gmap.addMarker(getMarker(latLngs.get(latLngs.size() - 1), R.drawable.ic_flag_red).title(getString(R.string.fim)));
        this.gmap.addPolyline(polylineOptions);
    }

    private boolean zoomIn() {
        if (latLngBounds != null) {
            gmap.animateCamera(
                    CameraUpdateFactory.newLatLngBounds(latLngBounds, 100)
            );
        }
        return true;
    }

    private MarkerOptions getMarker(LatLng position, @DrawableRes int iconId) {
        return new MarkerOptions()
                .icon(new ResourcesUtils(getContext()).bitmapDescriptorFromVector(iconId))
                .position(position)
                .alpha(1)
                .flat(false)
                .anchor(0, (float) 1.1);
    }
}
