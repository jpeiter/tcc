package br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseController {

    public static FirebaseDatabase getInstance(){
        return FirebaseDatabase.getInstance();
    }

    public static DatabaseReference getDatabase() {
        return getInstance().getReference();
    }

    public static DatabaseReference getDatabase(String dbPath) {
        String[] children = dbPath.split("/");
        DatabaseReference db = getDatabase();

        for (int i = 0; i < children.length; i++) {
            db = db.child(children[i]);
        }

        return db;
    }

    public static Task<Void> setValue(String dbPath, Object data) {
        DatabaseReference db = getDatabase(dbPath);
        return db.setValue(data);
    }
}