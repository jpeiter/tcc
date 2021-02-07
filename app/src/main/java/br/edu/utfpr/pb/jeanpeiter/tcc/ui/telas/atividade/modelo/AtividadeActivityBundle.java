package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class AtividadeActivityBundle {

    private AtividadeActivityMetodo metodo;
    private Long tempoDecorrido;
    private Long termino;

    public AtividadeActivityBundle(AtividadeActivityMetodo metodo) {
        this.metodo = metodo;
    }

    public enum AtividadeActivityMetodo {
        INICIAR,
        PAUSAR,
        RETOMAR,
        FINALIZAR
    }
}
