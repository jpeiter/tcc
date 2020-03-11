package br.edu.utfpr.pb.jeanpeiter.tcc.usuario;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import lombok.Getter;

@Getter
public enum Sexo {

    FEMININO("Feminino"),
    MASCULINO("Masculino");

    private String descricao;

    Sexo(String descricao) {
        this.descricao = descricao;
    }

    public static Sexo getByResourceId(int id) {
        switch (id) {
            case R.id.rb_feminino:
                return FEMININO;
            case R.id.rb_masculino:
                return MASCULINO;
            default:
                return null;
        }
    }
}
