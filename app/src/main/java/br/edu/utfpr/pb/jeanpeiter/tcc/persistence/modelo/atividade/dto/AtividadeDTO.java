package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
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
@Entity(tableName = "atividade", indices = {@Index("inicio")}
)
public class AtividadeDTO {

    // Atividade._id
    @PrimaryKey
    private Long _id;

    // Atividade.inicio
    @ColumnInfo(name = "inicio")
    private Long i;

    // Atividade.termino
    @ColumnInfo(name = "termino")
    private Long te;

    // Atividade.tipo
    @ColumnInfo(name = "tipo")
    private String ti;

    //Atividade.distancia
    @ColumnInfo(name = "distancia")
    private Double di;

    // Atividade.velocidade
    @ColumnInfo(name = "velocidade")
    private Double v;

    // Atividade.duracao
    @ColumnInfo(name = "duracao")
    private Long du;

    // Atividade.ritmo
    @ColumnInfo(name = "ritmo")
    private Double r;

    // Atividade.calorias
    @ColumnInfo(name = "calorias")
    private Double c;

    // Atividade.pontos
    @ColumnInfo(name = "pontos")
    private Long p;

}