package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
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
@Entity(tableName = "atividade_posicao")
public class AtividadePosicaoDTO {

    // AtividadePosicao.latitude
    @PrimaryKey(autoGenerate = true)
    private Long _id;

    @ColumnInfo(name = "atividadeId")
    private String aId;

    // AtividadePosicao.latitude
    @ColumnInfo(name = "latitude")
    private Double la;

    // AtividadePosicao.longitude
    @ColumnInfo(name = "longitude")
    private Double lo;

    // AtividadePosicao.ordem
    @ColumnInfo(name = "ordem")
    private Long o;



}
