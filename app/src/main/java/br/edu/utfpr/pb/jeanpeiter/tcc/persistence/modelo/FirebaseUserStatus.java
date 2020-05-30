package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo;

import android.content.Context;

import com.google.firebase.database.ServerValue;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences.AppSharedPreferences;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class FirebaseUserStatus {

    private String status;
    private String nome;
    private final Object ultimoAcesso = ServerValue.TIMESTAMP;

    public static FirebaseUserStatus conectado(Context context) {
        FirebaseUserStatus user = new FirebaseUserStatus();
        user.setNome(new AppSharedPreferences(context).getUsuario().getNome());
        user.setStatus("CONECTADO");
        return user;
    }

}