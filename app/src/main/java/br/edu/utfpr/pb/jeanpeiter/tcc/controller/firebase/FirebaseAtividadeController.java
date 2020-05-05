package br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase;

import com.google.android.gms.tasks.Task;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;

public class FirebaseAtividadeController {

    public static Task<Void> save(Atividade atividade) {
        String userId = FirebaseUserController.getUser().getUid();
        String atividadeId = String.valueOf(atividade.get_id());
        String path = "activities".concat("/").concat(userId ).concat("/").concat(atividadeId);
        return FirebaseController.setValue(path, atividade);
    }
}