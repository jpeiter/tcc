package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao;

import android.location.Location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtividadePosicao {

    private Long _id;

    private Long atividadeId;

    private Long ordem;

    private Double latitude;

    private Double longitude;

    public AtividadePosicao(Long atividadeId, Long ordem, Location location) {
        this.atividadeId = atividadeId;
        this.ordem = ordem;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }
}
