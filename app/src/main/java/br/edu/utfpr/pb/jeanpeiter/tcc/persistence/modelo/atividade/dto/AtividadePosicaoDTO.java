package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Entity(tableName = "atividade_posicao")
public class AtividadePosicaoDTO {


    // AtividadePosicao.latitude
//    @PrimaryKey(autoGenerate = true)
    private Double _id;

    // AtividadePosicao.latitude
//    @ColumnInfo
    private Double la;

    // AtividadePosicao.longitude
    private Double lo;

    // AtividadePosicao.ordem
    private Long o;

    public AtividadePosicaoDTO(AtividadePosicao posicao) {
            this.la = posicao.getLatitude();
            this.lo = posicao.getLongitude();
            this.o = posicao.getOrdem();
    }

    public AtividadePosicao parse() {
        return AtividadePosicao.builder()
                .latitude(this.la)
                .longitude(this.lo)
                .ordem(this.o)
                .build();
    }
}
