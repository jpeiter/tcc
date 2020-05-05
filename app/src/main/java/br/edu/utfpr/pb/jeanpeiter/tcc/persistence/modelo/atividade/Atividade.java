package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "atividade")
public class Atividade {

    // Identificador da atividade
    @PrimaryKey
    private Long _id;

    // Identificador do usuário
    @ColumnInfo(name = "usuario_uid")
    private String usuarioUid;

    // Início da atividade, em milissegundos
    @ColumnInfo
    private Long inicio;

    // Término da atividade, em milissegundos
    @ColumnInfo
    private Long termino;

    // Tipo da atividade
    @ColumnInfo
    private AtividadeTipo tipo;

    // Distância total, em metros
    @ColumnInfo
    private Double distancia;

    // Velocidade média, em m/s
    @ColumnInfo
    private Double velocidade;

    // Duração total, em segundos
    @ColumnInfo
    private Long duracao;

    // Ritmo médio, em min/km
    @ColumnInfo
    private Double ritmo;

    // Calorias gastas, em kcal
    @ColumnInfo
    private Double calorias;

    // Pontuação final obtida
    @ColumnInfo
    private Long pontos;

    // Coordenadas do trajeto
    private List<AtividadePosicao> posicoes = new ArrayList<>();
}
