package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.modelo;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AtividadeFragmentBundle {

    private AtividadeFragmentMetodo metodo;
    private Atividade atividade;

    public AtividadeFragmentBundle(AtividadeFragmentMetodo metodo) {
        this.metodo = metodo;
    }

    public enum AtividadeFragmentMetodo {
        ATUALIZAR,
        PAUSAR,
        RETOMAR
    }
}
