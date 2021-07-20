package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao;

import android.location.Location;

import androidx.room.Ignore;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadePosicaoDTO;
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

    private Long ordem;

    private Double latitude;

    private Double longitude;

    // Sincronizado online
    @Ignore
    private boolean sincronizado;

    public AtividadePosicao(Long ordem, Location location) {
        this.ordem = ordem;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public AtividadePosicao(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public AtividadePosicaoDTO toDto(String atividadeId) {
        return AtividadePosicaoDTO.builder()
                .aId(atividadeId)
                .la(this.getLatitude())
                .lo(this.getLongitude())
                .o(this.getOrdem())
                .s(this.isSincronizado() ? "S" : "N")
                .build();
    }

    public AtividadePosicaoDTO toDto() {
        return AtividadePosicaoDTO.builder()
                .la(this.getLatitude())
                .lo(this.getLongitude())
                .o(this.getOrdem())
                .s(this.isSincronizado() ? "S" : "N")
                .build();
    }

    public AtividadePosicao parse(AtividadePosicaoDTO dto) {
        return AtividadePosicao.builder()
                .latitude(dto.getLa())
                .longitude(dto.getLo())
                .ordem(dto.getO())
                .sincronizado("S".equals(dto.getS()))
                .build();
    }
}
