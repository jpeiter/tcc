package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.dupla;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseAtividadeDuplaController;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseUserStatusController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ListenerActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.AtividadeActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.dupla.fragments.selecionarparceiro.SelecionarParceiroFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.DialogUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.IntentUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
public class SelecionarParceiroActivity extends AppCompatActivity implements GenericActivity, ListenerActivity {

    public static final String PARCEIRO_UID_EXTRA = "PARCEIRO_UID_EXTRA";
    private Button btnProxima;

    private Map<String, String> solicitacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_parceiro);
        initViews();
        initListeners();
        this.conectar();
        new FragmentUtils().loadFragment(this, R.id.fl_selecionar_parceiro, new SelecionarParceiroFragment());
    }

    @Override
    public void finish() {
        this.desconectar();
        super.finish();
    }

    @Override
    protected void onStop() {
        this.desconectar();
        super.onStop();
    }

    @Override
    protected void onResume() {
        this.conectar();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        this.conectar();
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        this.desconectar();
        super.onBackPressed();
    }

    @Override
    public void initViews() {
        this.setBtnProxima(findViewById(R.id.btn_proxima_solicitacao));
    }

    @Override
    public void initListeners() {
        this.monitorarPendentes();

        getBtnProxima().setOnClickListener(v -> {
            Map<String, String> solicitacoesAtual = new HashMap<>(this.solicitacoes);
            CharSequence[] nomeParceiros = Arrays.copyOf(solicitacoesAtual.values().toArray(), solicitacoesAtual.size(), CharSequence[].class);

            AtomicInteger checkedItem = new AtomicInteger();
            new DialogUtils().build(SelecionarParceiroActivity.this, getString(R.string.selecione))
                    .setSingleChoiceItems(nomeParceiros, 0, (dialog, which) -> {
                        checkedItem.set(which);
                    })
                    .setNegativeButton(getString(R.string.cancelar), (dialog, which) -> dialog.cancel())
                    .setPositiveButton(getString(R.string.confirmar), (dialog, which) -> {
                        Map.Entry<String, String> entry = (Map.Entry<String, String>) solicitacoesAtual.entrySet().toArray()[checkedItem.get()];
                        String parceiroUid = entry.getKey();
                        iniciarAtividadeDupla(parceiroUid);
                    })
                    .create().show();
        });
    }

    private void iniciarAtividadeDupla(String parceiroUid) {
        FirebaseAtividadeDuplaController.getInstance().confirmar(parceiroUid);
        Intent i = new Intent(this, AtividadeActivity.class);
        i.putExtra(PARCEIRO_UID_EXTRA, parceiroUid);
        AtividadeTipo.fromIntent(getIntent()).toIntent(i);
        new IntentUtils().startActivity(this, i);
        this.finish();
    }

    private void monitorarPendentes() {
        solicitacoes = new HashMap<>();
        FirebaseAtividadeDuplaController.getInstance().monitorarPendentes(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getValue(String.class).equals(FirebaseAtividadeDuplaController.VALOR_CONFIRMACAO)) {
                    iniciarAtividadeDupla(dataSnapshot.getKey());
                } else {
                    solicitacoes.put(dataSnapshot.getKey(), dataSnapshot.getValue(String.class));
                    atualizarUi();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getValue(String.class).equals(FirebaseAtividadeDuplaController.VALOR_CONFIRMACAO)) {
                    iniciarAtividadeDupla(dataSnapshot.getKey());
                } else {
                    solicitacoes.put(dataSnapshot.getKey(), dataSnapshot.getValue(String.class));
                    atualizarUi();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                solicitacoes.remove(dataSnapshot.getKey());
                atualizarUi();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void atualizarUi() {
        getBtnProxima().setVisibility(solicitacoes.size() > 0 ? View.VISIBLE : View.GONE);
        String nConvites = getString(R.string.convites_de_atividade) + solicitacoes.size();
        getBtnProxima().setText(nConvites);
    }

    private void conectar() {
        AsyncTask.execute(() -> FirebaseUserStatusController.conectar(SelecionarParceiroActivity.this.getBaseContext()));
    }

    private void desconectar() {
        AsyncTask.execute(FirebaseUserStatusController::desconectar);
    }

    public void btnVoltarClick(View view) {
        this.finish();
    }
}
