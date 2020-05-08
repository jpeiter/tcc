package br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase;

import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadePosicaoDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;

public class FirebaseAtividadeController {

    public static Task<Void> save(Atividade atividade, Runnable acao) {
        String userId = FirebaseUserController.getUser().getUid();
        String atividadeId = String.valueOf(atividade.get_id());
        String path = "activities".concat("/").concat(userId).concat("/").concat(atividadeId);
        return FirebaseController.setValue(path, atividade.toDto()).addOnSuccessListener(success -> {
            savePosicoes(atividadeId, atividade.getPosicoes(), acao);
        });

    }

    private static Task<Void> savePosicoes(String atividadeId, List<AtividadePosicao> posicoes, Runnable acao) {
        String userId = FirebaseUserController.getUser().getUid();
        String path = "paths".concat("/").concat(userId).concat("/").concat(atividadeId);
        List<AtividadePosicaoDTO> dtos = posicoes.stream().map(AtividadePosicaoDTO::new).collect(Collectors.toList());
        return FirebaseController.setValue(path, dtos).addOnSuccessListener(success -> {
            acao.run();
        });
    }
}