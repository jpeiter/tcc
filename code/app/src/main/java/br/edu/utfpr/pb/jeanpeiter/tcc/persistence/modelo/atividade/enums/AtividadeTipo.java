package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums;

import android.content.Intent;

public enum AtividadeTipo {

    DUPLA,
    SOZINHO;

    private static final String name = AtividadeTipo.class.getName();

    public void toIntent(Intent intent) {
        intent.putExtra(name, ordinal());
    }

    public static AtividadeTipo fromIntent(Intent intent) {
        if (!intent.hasExtra(name)) throw new IllegalStateException();
        return values()[intent.getIntExtra(name, -1)];
    }

}
