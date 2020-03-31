package br.edu.utfpr.pb.jeanpeiter.tcc.usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Perfil {

    private LocalDate nascimento;

    private String nome;

    private SexoEnum sexo;

    private BigDecimal altura;

    private BigDecimal peso;

    public void setNascimento(Date date) {
        this.nascimento = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}