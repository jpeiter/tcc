package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums;

import android.content.Intent;

public enum AtividadeTipo {

    DUPLA,
    SOZINHO;

    private static final String name = AtividadeTipo.class.getName();

    public static AtividadeTipo fromDto(String dto) {
        if (!"S".equals(dto) && !"D".equals(dto)) throw new IllegalStateException();
        return "S".equals(dto) ? SOZINHO : DUPLA;
    }

    public String toDto() {
        return values()[ordinal()].toString().substring(0, 1).toUpperCase();
    }

    public void toIntent(Intent intent) {
        intent.putExtra(name, ordinal());
    }

    public static AtividadeTipo fromIntent(Intent intent) {
        if (!intent.hasExtra(name)) throw new IllegalStateException();
        return values()[intent.getIntExtra(name, -1)];
    }

}
