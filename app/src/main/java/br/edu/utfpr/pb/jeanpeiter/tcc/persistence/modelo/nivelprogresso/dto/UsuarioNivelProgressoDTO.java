package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.dto;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
@Entity(tableName = "usuario_nivel",
        indices = {@Index("nivel"),
                @Index("datahora"),
                @Index("pontuacao")})
public class UsuarioNivelProgressoDTO {

    @NonNull
    @PrimaryKey
    private String _id;

    // UsuarioNivelProgresso.usuarioUid
    @ColumnInfo(name = "userid")
    private String uid;

    // UsuarioNivelProgresso.nivel (ordinal)
    @ColumnInfo(name = "nivel")
    private int n;

    // Data e hora que o nível foi atingido
    @ColumnInfo(name = "datahora")
    private long d;

    // Pontuação total
    @ColumnInfo(name = "pontuacao")
    private int p;

}
