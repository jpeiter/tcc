package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso;


import androidx.room.Entity;

import java.time.LocalDateTime;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.dto.UsuarioNivelProgressoDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.DateUtils;
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
public class UsuarioNivelProgresso {

    // Identificador
    private String id;

    // Identificador do usuário
    private String usuarioUid;

    // Nível de progesso
    private NivelProgresso nivel;

    // Data e hora que o nível foi atingido
    private LocalDateTime dataHora;

    // Pontuação total
    private int pontuacao;

    public UsuarioNivelProgresso(String usuarioUid, NivelProgresso nivel, LocalDateTime dataHora, int pontuacao) {
        this.usuarioUid = usuarioUid;
        this.nivel = nivel;
        this.dataHora = dataHora;
        this.pontuacao = pontuacao;
    }

    public UsuarioNivelProgresso parse(UsuarioNivelProgressoDTO dto) {
        this.setId(dto.get_id());
        this.setUsuarioUid(dto.getUid());
        this.setNivel(NivelProgresso.values()[dto.getN()]);
        this.setDataHora(new DateUtils().millisToLocalDateTime(dto.getD()));
        this.setPontuacao(dto.getP());
        return this;
    }

    public UsuarioNivelProgressoDTO toDto() {
        return UsuarioNivelProgressoDTO.builder()
                ._id(this.getId())
                .uid(this.getUsuarioUid())
                .d(new DateUtils().localDateTimeToMillis(this.getDataHora()))
                .n(this.getNivel().ordinal())
                .p(this.getPontuacao())
                .build();
    }

}
