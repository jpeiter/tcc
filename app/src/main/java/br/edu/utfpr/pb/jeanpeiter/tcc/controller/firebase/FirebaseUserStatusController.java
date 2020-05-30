package br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.FirebaseUserStatus;

public class FirebaseUserStatusController {

    private static final String PATH = "info/connected";

    public static DatabaseReference getDatabase() {
        return FirebaseController.getDatabase(PATH);
    }

    public static Task<Void> conectar(Context context) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return FirebaseController.getDatabase(PATH + "/" + userId).setValue(FirebaseUserStatus.conectado(context));
    }

    public static Task<Void> desconectar() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return FirebaseController.getDatabase(PATH + "/" + userId).onDisconnect().removeValue();
    }

    public static Query usuariosConectados() {
        return FirebaseController.getDatabase(PATH).orderByChild("status").equalTo("CONECTADO");
    }


}