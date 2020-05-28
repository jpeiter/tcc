package br.edu.utfpr.pb.jeanpeiter.tcc.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.FirebaseUserStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class FirebaseUserStatusHolder extends RecyclerView.ViewHolder {

    private TextView tvNome;
    
    public FirebaseUserStatusHolder(@NonNull View itemView) {
        super(itemView);
        setTvNome(itemView.findViewById(R.id.tv_nome_selecionar_parceiro));
    }

    public void bind(@NonNull FirebaseUserStatus userStatus) {
        getTvNome().setText(userStatus.getNome());
    }
}
