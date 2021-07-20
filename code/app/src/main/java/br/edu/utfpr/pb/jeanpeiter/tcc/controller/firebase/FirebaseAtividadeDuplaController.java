package br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase;

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
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class FirebaseAtividadeDuplaController {

    private static FirebaseAtividadeDuplaController instance;

    private static final String PATH = "atividade_dupla";
    public static final String VALOR_CONFIRMACAO = "SOLICITACAO_CONFIRMADA";

    private final String userId = FirebaseUserController.getUser().getUid();

    @Setter(AccessLevel.PRIVATE)
    private String parceiroUid;

    @Setter(AccessLevel.PRIVATE)
    private String pathAtividadeDupla;

    @Setter(AccessLevel.PRIVATE)
    private Set<String> pathsPendencias;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private ChildEventListener listenerMonitorarPendentes;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private ValueEventListener listenerMonitorarParceiro;


    public synchronized static FirebaseAtividadeDuplaController getInstance() {
        if (instance == null) {
            instance = new FirebaseAtividadeDuplaController();
            instance.setPathsPendencias(new HashSet<>());
            String userId = FirebaseUserController.getUser().getUid();
            FirebaseDatabase.getInstance().goOnline();
            FirebaseController.getDatabase(PATH.concat("/").concat(userId).concat("/pendentes")).removeValue();
        }
        return instance;
    }


    /* Paths */
    private String apathAtividadeDupla() {
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

    /* Ações */
    public Task<Void> solicitar(String parceiroUid) {
        setParceiroUid(parceiroUid);
        String pathSolicitar = pathPendentesParceiro().concat("/").concat(this.userId);
        pathsPendencias.add(pathSolicitar);
        return FirebaseController.getDatabase(pathSolicitar).setValue(FirebaseUserController.getUser().getDisplayName());
    }

    public void confirmar(String parceiroUid) {
        setParceiroUid(parceiroUid);
        String pathConfirmar = pathPendentesParceiro().concat("/").concat(this.userId);
        FirebaseController.getDatabase(pathConfirmar).setValue(VALOR_CONFIRMACAO);
    }

    public void iniciar(String parceiroUid, String atividadeId) {
        setParceiroUid(parceiroUid);
        zerarPendencias();
        String pathAtividadeUser = pathAtividadeDupla().concat("/").concat(this.userId);
        FirebaseController.setValue(pathAtividadeUser, new Atividade(atividadeId, AtividadeTipo.DUPLA, AtividadeEstado.EM_ANDAMENTO).toDto());
    }

    private String pathAtividadeDupla() {
        if (this.pathAtividadeDupla == null) {
            List<String> uids = Arrays.asList(this.userId, this.parceiroUid);
            uids.sort(Comparator.naturalOrder());
            this.pathAtividadeDupla = PATH.concat("/")
                    .concat(uids.get(0))
                    .concat("_")
                    .concat(uids.get(1));
        }
        return this.pathAtividadeDupla;
    }

    public void atualizar(Atividade atividade) {
        String pathAtividadeUser = pathAtividadeDupla().concat("/").concat(this.userId);
        FirebaseController.setValue(pathAtividadeUser, atividade.toDto());
    }

    public void monitorarParceiro(ValueEventListener listenerMonitorarParceiro) {
        setListenerMonitorarParceiro(listenerMonitorarParceiro);
        String pathAtividadeParceiro = pathAtividadeDupla().concat("/").concat(this.parceiroUid);
        FirebaseController.getDatabase(pathAtividadeParceiro)
                .addValueEventListener(getListenerMonitorarParceiro());
    }

    /* Monitoramentos */
    public void monitorarPendentes(ChildEventListener listenerMonitorarPendentes) {
        setListenerMonitorarPendentes(listenerMonitorarPendentes);
        FirebaseController.getDatabase(pathPendentes())
                .addChildEventListener(getListenerMonitorarPendentes());
    }

    public void monitoraraParceiro(ValueEventListener listenerMonitorarParceiro) {
        setListenerMonitorarParceiro(listenerMonitorarParceiro);
        String pathAtividadeParceiro = pathAtividadeDupla().concat("/").concat(this.parceiroUid);
        FirebaseController.getDatabase(pathAtividadeParceiro).addValueEventListener(getListenerMonitorarParceiro());
    }

    /* Finalizar */
public void finalizar(Atividade atividade) {
    atualizar(atividade);
    FirebaseController.getDatabase(pathAtividadeDupla()).removeValue();
    if (getListenerMonitorarParceiro() != null) {
        FirebaseController.getDatabase(pathAtividadeDupla()
                .concat("/").concat(this.parceiroUid))
                .removeEventListener(getListenerMonitorarParceiro());
    }
    setParceiroUid(null);
    setPathAtividadeDupla(null);
    setPathsPendencias(null);
    instance = null;
}

    public void zerarPendencias() {
        FirebaseController.getDatabase(pathPendentes()).removeValue();
        if (getListenerMonitorarPendentes() != null) {
            FirebaseController.getDatabase(pathPendentes())
                    .removeEventListener(getListenerMonitorarPendentes());
        }
    }

}