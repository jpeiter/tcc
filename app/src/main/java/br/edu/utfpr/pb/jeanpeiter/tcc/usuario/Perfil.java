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

    private String nascimento;

    private String nome;

    private SexoEnum sexo;

    private Double altura;

    private Double peso;

    public void setAltura(BigDecimal altura){
        this.altura = altura.doubleValue();
    }
    public void setPeso(BigDecimal peso){
        this.peso = peso.doubleValue();
    }

    public void setNascimento(Date date) {
        this.nascimento = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }
}