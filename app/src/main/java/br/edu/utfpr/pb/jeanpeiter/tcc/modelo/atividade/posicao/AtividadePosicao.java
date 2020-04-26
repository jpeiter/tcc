package br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.posicao;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AtividadePosicao {

    private String atividadeId;

    private Long ordem;

    private Double latitude;

    private Double longitude;

    public AtividadePosicao(String atividadeId, Long ordem, Location location) {
        this.atividadeId = atividadeId;
        this.ordem = ordem;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }
}
