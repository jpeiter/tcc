package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;


import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeEstado;
import lombok.Getter;
import lombok.Setter;

public class AtividadeEstadoSingleton {

    private static AtividadeEstadoSingleton instance;

    @Getter
    private AtividadeEstado estado;

    public void setEstado(AtividadeEstado estado) {
        this.estado = estado;
    }

    public static AtividadeEstadoSingleton getInstance() {
        if (instance == null) {
            instance = new AtividadeEstadoSingleton();
        }
        return instance;
    }

    public void destroy() {
        estado = null;
        instance = null;
    }

    private boolean verificarEstado(AtividadeEstado estado) {
        return estado.equals(getEstado());
    }

    public boolean isEmAndamento() {
        return verificarEstado(AtividadeEstado.EM_ANDAMENTO);
    }

    public boolean isPausada() {
        return verificarEstado(AtividadeEstado.PAUSADA);
    }

    public boolean isFinalizada() {
        return verificarEstado(AtividadeEstado.FINALIZADA);
    }

}
