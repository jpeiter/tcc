package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.resumo;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.BigDecimalUtils;
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
public class AtividadeResumo {

    private String _id;
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private AtividadeTipo tipo;
    private Double distancia;
    private Double velocidade;
    private Duration duracao;
    private Duration ritmo;
    private BigDecimal calorias;
    private Long pontos;
    private List<AtividadePosicao> posicoes;
    private String parceiroNome;

    public AtividadeResumo(Atividade dto) {
        DateUtils dateUtils = new DateUtils();
        this._id = dto.get_id();
        this.inicio = dateUtils.millisToLocalDateTime(dto.getInicio());
        this.distancia = dto.getDistancia();
        this.duracao = Duration.ofSeconds(dto.getDuracao());
        this.parceiroNome = dto.getParceiroNome();
    }
}
