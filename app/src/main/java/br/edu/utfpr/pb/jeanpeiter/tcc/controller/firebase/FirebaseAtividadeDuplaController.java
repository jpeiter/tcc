package br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase;

import android.app.Application;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import lombok.AccessLevel;
import lombok.Setter;

public class FirebaseAtividadeDuplaController {

    private static FirebaseAtividadeDuplaController instance;

    public static final String VALOR_CONFIRMACAO = Application.getProcessName()
            .concat(".")
            .concat(FirebaseAtividadeDuplaController.class.getCanonicalName())
            .concat(".SOLICITACAO_CONFIRMADA");

    public synchronized static FirebaseAtividadeDuplaController getInstance() {
        if (instance == null) {
            instance = new FirebaseAtividadeDuplaController();
            FirebaseDatabase.getInstance().goOnline();
            FirebaseController.getDatabase(PATH.concat("/").concat(FirebaseUserController.getUser().getUid()).concat("/pendentes")).onDisconnect().removeValue();
        }
        return instance;
    }

    public static final String PATH = "atividade_dupla";

    @Setter(AccessLevel.PRIVATE)
    private String parceiroUid;
    private final String userId = FirebaseUserController.getUser().getUid();
    private String pathAtividadeDupla;

    private ChildEventListener listenerMonitorarPendentes;
    private ValueEventListener listenerMonitorarParceiro;

    private Set<String> pathsPendencias = new HashSet<>();

    /* Paths */
    private String pathAtividadeDupla() {
        if (this.pathAtividadeDupla == null) {
            List<String> uids = Arrays.asList(this.userId, this.parceiroUid);
            uids.sort(Comparator.naturalOrder());
            this.pathAtividadeDupla = PATH.concat("/").concat(uids.get(0)).concat("_").concat(uids.get(1));
        }
        return pathAtividadeDupla;
    }

    private String pathPendentes() {
        return PATH.concat("/").concat(userId).concat("/pendentes");
    }

    private String pathPendentesParceiro() {
        return PATH.concat("/").concat(parceiroUid).concat("/pendentes");
    }

    public Task<Void> solicitar(String parceiroUid) {
        setParceiroUid(parceiroUid);
        String pathSolicitar = pathPendentesParceiro().concat("/").concat(this.userId);
        pathsPendencias.add(pathSolicitar);
        return FirebaseController.getDatabase(pathSolicitar).setValue(FirebaseUserController.getUser().getDisplayName());
    }

    public Task<Void> confirmar(String parceiroUid) {
        setParceiroUid(parceiroUid);
        String pathConfirmar = pathPendentesParceiro().concat("/").concat(this.userId);
        return FirebaseController.getDatabase(pathConfirmar).setValue(VALOR_CONFIRMACAO);
    }

    public Task<Void> iniciar(String parceiroUid, String atividadeId) {
        setParceiroUid(parceiroUid);
        String pathAtividadeUser = pathAtividadeDupla().concat("/").concat(this.userId);
        return FirebaseController.setValue(pathAtividadeUser, new Atividade(atividadeId, AtividadeTipo.DUPLA).toDto());
    }

    public void atualizar(Atividade atividade) {
        String pathAtividadeUser = pathAtividadeDupla().concat("/").concat(this.userId);
        FirebaseController.setValue(pathAtividadeUser, atividade.toDto());
    }

    public void monitorarPendentes(ChildEventListener listener) {
        listenerMonitorarPendentes = listener;
        FirebaseController.getDatabase(pathPendentes()).addChildEventListener(listenerMonitorarPendentes);
    }

    public void monitorarParceiro(ValueEventListener listener) {
        listenerMonitorarParceiro = listener;
        String pathAtividadeParceiro = pathAtividadeDupla().concat("/").concat(this.parceiroUid);
        FirebaseController.getDatabase(pathAtividadeParceiro).addValueEventListener(listenerMonitorarParceiro);
    }

    public void zerarPendencias() {
        for (String pathPendencia : pathsPendencias) {
            FirebaseController.getDatabase(pathPendencia).removeValue();
        }

    }

}