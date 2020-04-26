package br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.enums.AtividadeTipo;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.posicao.AtividadePosicao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Atividade {

    // Identificador da atividade
    private String id;

    // Identificador do usuário
    private String usuarioUid;

    // Início da atividade, em milissegundos
    private Long inicio;

    // Término da atividade, em milissegundos
    private Long termino;

    // Tipo da atividade
    private AtividadeTipo tipo;

    // Distância total, em metros
    private Double distancia;

    // Velocidade média, em m/s
    private Double velocidade;

    // Duração total, em segundos
    private Long duracao;

    // Ritmo médio, em min/km
    private Double ritmo;

    // Calorias gastas, em kcal
    private Double calorias;

    // Pontuação final obtida
    private Long pontos;

    // Coordenadas do trajeto
    private List<AtividadePosicao> posicoes = new ArrayList<>();
}
