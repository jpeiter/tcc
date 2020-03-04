package br.edu.utfpr.pb.jeanpeiter.tcc.usuario;

import com.google.firebase.auth.FirebaseUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.time.OffsetDateTime.ofInstant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Perfil {

    private String id;

    private LocalDate nascimento;

    private String nome;

    private Sexo sexo;

    private BigDecimal altura;

    private BigDecimal peso;

    private FirebaseUser usuario;

    public void setNascimento(Date date){
        this.nascimento = LocalDate.from(ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

}
