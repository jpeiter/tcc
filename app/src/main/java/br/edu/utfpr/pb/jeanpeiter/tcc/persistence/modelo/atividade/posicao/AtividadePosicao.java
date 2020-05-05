package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao;

import android.location.Location;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "atividadeposicao")
public class AtividadePosicao {

    @PrimaryKey
    private Long _id;

    private Long atividadeId;

    @ColumnInfo
    private Long ordem;

    @ColumnInfo
    private Double latitude;

    @ColumnInfo
    private Double longitude;

    public AtividadePosicao(Long atividadeId, Long ordem, Location location) {
        this.atividadeId = atividadeId;
        this.ordem = ordem;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }
}
