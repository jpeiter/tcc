package br.edu.utfpr.pb.jeanpeiter.tcc.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseAtividadeDuplaController;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseUserController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.FirebaseUserStatus;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ListenerActivity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class FirebaseUserStatusHolder extends RecyclerView.ViewHolder implements ListenerActivity {

    public static final String EXTRA_UID_PARCEIRO = "UID_PARCEIRO";


    private TextView tvNome;
    private String uid;

    public FirebaseUserStatusHolder(@NonNull View itemView) {
        super(itemView);
        initListeners();
        setTvNome(itemView.findViewById(R.id.tv_nome_selecionar_parceiro));
    }

    public void bind(@NonNull FirebaseUserStatus userStatus, String uid) {
        String voce = uid.equals(FirebaseUserController.getUser().getUid()) ? " (você)" : " ";
        getTvNome().setText(userStatus.getNome() + voce);
        this.uid = uid;
    }

    @Override
    public void initListeners() {
        this.itemView.setOnClickListener(v -> {
            if (!uid.equals(FirebaseUserController.getUser().getUid())) {
                FirebaseAtividadeDuplaController.getInstance().solicitar(uid);
            }
        });
    }
}
