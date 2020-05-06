package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AtividadeDTO {

    // Atividade._id
    private Long _id;

    // Atividade.inicio
    private Long i;

    // Atividade.termino
    private Long te;

    // Atividade.tipo
    private String ti;

    //Atividade.distancia
    private Double di;

    // Atividade.velocidade
    private Double v;

    // Atividade.duracao
    private Long du;

    // Atividade.ritmo
    private Double r;

    // Atividade.calorias
    private Double c;

    // Atividade.pontos
    private Long p;

    public AtividadeDTO(Atividade atividade){
        this._id = atividade.get_id();
        this.i = atividade.getInicio();
        this.te = atividade.getTermino();
        this.ti = atividade.getTipo() == AtividadeTipo.SOZINHO ? "S" : "D";
        this.di = atividade.getDistancia();
        this.v = atividade.getVelocidade();
        this.du = atividade.getDuracao();
        this.r = atividade.getRitmo();
        this.c = atividade.getCalorias();
        this.p = atividade.getPontos();
    }

    public Atividade parse(){
        return Atividade.builder()
                ._id(get_id())
                .inicio(getI())
                .termino(getTe())
                .tipo(getTi() == "S" ? AtividadeTipo.SOZINHO : AtividadeTipo.DUPLA)
                .distancia(getDi())
                .duracao(getDu())
                .ritmo(getR())
                .calorias(getC())
                .pontos(getP())
            .build();
    }

}
