package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.firebase.database.annotations.NotNull;

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
    @NonNull
    private String _id;

    // Atividade.usuarioUid
    @ColumnInfo(name = "userid")
    private String uid;

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
    private int r;

    // Atividade.calorias
    @ColumnInfo(name = "calorias")
    private Long c;

    // Atividade.pontos
    @ColumnInfo(name = "pontos")
    private Long p;

    // Atividade.estado
    @ColumnInfo(name = "estado")
    private int e;

    // Uid do parceiro
    @ColumnInfo(name = "parceiro_uid")
    private String pId;

    // Nome do parceiro
    @ColumnInfo(name = "parceiro_nome")
    private String pNo;

    // Atividade.sincronizado
    @ColumnInfo(name = "sincronizado")
    private String s;

}
