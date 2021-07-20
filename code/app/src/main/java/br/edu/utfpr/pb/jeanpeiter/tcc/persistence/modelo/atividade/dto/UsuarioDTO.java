package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Sexo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;
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
public class UsuarioDTO {

    private String nascimento;

    private String nome;

    private String sexo;

    private Double altura;

    private Double peso;

    public Usuario parse(String uid) {
        return Usuario.builder()
                .uid(uid)
                .nascimento(this.getNascimento())
                .nome(this.getNome())
                .sexo(Sexo.FEMININO.equals(getSexo()) ? Sexo.FEMININO : Sexo.MASCULINO)
                .altura(this.getAltura())
                .peso(getPeso())
            .build();
    }

}
