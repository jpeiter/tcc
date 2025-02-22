package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sexo {

    FEMININO("Feminino"),
    MASCULINO("Masculino");

    private String descricao;

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
