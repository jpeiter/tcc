package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.maps;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeEstadoSingleton;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.data.LocationObservedData;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.AtividadeActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.modelo.AtividadeActivityBundle;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.LocationUtils;
import lombok.Getter;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private final float zoomCamera = 20;
    private final int paddingMap = 80;
    private boolean isZoomedOut = false;

    @Getter
    private GoogleMap gmap;

    private LocationUtils locationUtils;
    private PolylineOptions polylineOptions;
    private LatLngBounds latLngBounds;
    private LatLng ultimaPosicao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_mapa, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        polylineOptions = new PolylineOptions()
                .color(R.color.primariaClaro)
                .width(18)
                .endCap(new RoundCap())
                .startCap(new RoundCap())
                .jointType(JointType.ROUND);
        locationUtils = new LocationUtils();

        return parent;
    }

    @Override
    public void onStop() {
        if (!(getActivity() instanceof AtividadeActivity)) {
            if (gmap != null) {
                gmap.clear();
            }
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
            if (gmap != null) {
                gmap.clear();
                this.gmap.clear();
                polylineOptions = new PolylineOptions();
            }
        }
        super.onDestroy();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMyLocationEnabled(true);
        gmap.setIndoorEnabled(true);
    }

    // Subscribe no LocalizacaoListener
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void subscribe(LocationObservedData data) {
        if (data != null && data.getLocation() != null) {
            LatLng latLng = locationUtils.toLatLng(data.getLocation());
            ultimaPosicao = latLng;
            if (AtividadeEstadoSingleton.getInstance().isEmAndamento()) {
                moverCamera(latLng);
            }
        }
    }

    // Subscribe na atividade enquanto está em andamento
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void subscribe(AtividadePosicao posicao) {
        if (posicao != null) {
            LatLng latLng = locationUtils.toLatLng(posicao);

            latLngBounds = latLngBounds == null ?
                    new LatLngBounds.Builder()
                            .include(latLng)
                            .build() :
                    latLngBounds.including(latLng);
            addPolyLine(latLng);
        }
    }

    // Subscribe nas ações de Pausar e Retomar
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(AtividadeActivityBundle bundle) {
        switch (bundle.getMetodo()) {
            case PAUSAR:
                zoomOut();
                break;
            case RETOMAR:
                isZoomedOut = false;
                moverCamera(ultimaPosicao);
                break;
            case FINALIZAR:
                this.gmap.clear();
                polylineOptions = new PolylineOptions();
                break;
        }
    }

    private void moverCamera(LatLng latLng) {
        if (!this.isZoomedOut && this.isMapReady() && latLng != null) {
            this.gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, this.zoomCamera));
        }
    }

    private void zoomOut() {
        if (this.isMapReady() && this.latLngBounds != null) {
            this.gmap.animateCamera(CameraUpdateFactory.newLatLngBounds(this.latLngBounds, this.paddingMap));
            isZoomedOut = true;
        }
    }

    private void addPolyLine(LatLng latLng) {
        if (isMapReady()) {
            this.polylineOptions.add(latLng);
            this.gmap.addPolyline(this.polylineOptions);
        }
    }


    private boolean isMapReady() {
        return gmap != null;
    }
}
