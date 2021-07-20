package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums;

public enum AtividadeEstado {

    EM_ANDAMENTO,
    PAUSADA,
    FINALIZADA;

    public static AtividadeEstado fromDto(int ordinal) {
        return values()[ordinal];
    }

    public int toDto() {
        return this.ordinal();
    }


}
