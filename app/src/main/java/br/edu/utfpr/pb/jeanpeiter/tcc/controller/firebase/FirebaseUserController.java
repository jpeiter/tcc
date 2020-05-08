package br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase;

import android.content.Context;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.login.LoginActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.IntentUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences.AppSharedPreferences;

public class FirebaseUserController {

    public static FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static String getFirstName() {
        return getUser().getDisplayName().split(" ")[0];
    }

    public static Task<Void> signOut(Context context) {
        return AuthUI.getInstance().signOut(context).addOnCompleteListener(command -> {
            new AppSharedPreferences(context).removeUsuario();
            new IntentUtils().startActivity(context, LoginActivity.class);
        });
    }

    public static Task<Void> salvarPerfil(Usuario usuario) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return FirebaseController.setValue("users/" + userId, usuario);
    }
}