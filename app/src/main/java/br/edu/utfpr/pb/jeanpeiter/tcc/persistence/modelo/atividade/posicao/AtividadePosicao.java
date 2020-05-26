package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao;

import android.location.Location;

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

    public AtividadePosicao(Long ordem, Location location) {
        this.ordem = ordem;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public AtividadePosicaoDTO toDto(String atividadeId) {
        return AtividadePosicaoDTO.builder()
                .aId(atividadeId)
                .la(this.getLatitude())
                .lo(this.getLongitude())
                .o(this.getOrdem())
                .build();
    }

    public AtividadePosicaoDTO toDto() {
        return AtividadePosicaoDTO.builder()
                .la(this.getLatitude())
                .lo(this.getLongitude())
                .o(this.getOrdem())
                .build();
    }

    public AtividadePosicao parse(AtividadePosicaoDTO dto) {
        return AtividadePosicao.builder()
                .latitude(dto.getLa())
                .longitude(dto.getLo())
                .ordem(dto.getO())
                .build();
    }
}
