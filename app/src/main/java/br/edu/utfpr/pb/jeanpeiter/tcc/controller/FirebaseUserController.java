package br.edu.utfpr.pb.jeanpeiter.tcc.controller;

import android.content.Context;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.utfpr.pb.jeanpeiter.tcc.activity.login.LoginActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.usuario.Perfil;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.IntentUtils;

public class FirebaseUserController {

    public static FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static String getFirstName() {
        return getUser().getDisplayName().split(" ")[0];
    }

    public static Task<Void> signOut(Context context) {
        return AuthUI.getInstance().signOut(context).addOnCompleteListener(command -> {
            new IntentUtils().startActivity(context, LoginActivity.class);
        });
    }

    public static Task<Void> salvarPerfil(Perfil perfil) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return FirebaseController.setValue("perfil/" + userId, perfil);
    }
}