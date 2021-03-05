package br.edu.utfpr.pb.jeanpeiter.tcc.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseUserController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.FirebaseUserStatus;

public class FirebaseUserStatusRecyclerAdapter extends FirebaseRecyclerAdapter<FirebaseUserStatus, FirebaseUserStatusHolder> {

    FirebaseRecyclerOptions<FirebaseUserStatus> options;

    public FirebaseUserStatusRecyclerAdapter(@NonNull FirebaseRecyclerOptions<FirebaseUserStatus> options) {
        super(options);
        this.options = options;
        startListening();
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseUserStatusHolder holder, int position, @NonNull FirebaseUserStatus model) {
        if (super.getItemCount() > 0) {
            holder.bind(model, getRef(position).getKey());
        }
    }

    @NonNull
    @Override
    public FirebaseUserStatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_selecionar_parceiro, parent, false);
        return new FirebaseUserStatusHolder(view);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
    }

    @Override
    public void onChildChanged(@NonNull ChangeEventType type, @NonNull DataSnapshot snapshot, int newIndex, int oldIndex) {
        if (!snapshot.getKey().equals(FirebaseUserController.getUser().getUid())) {
            if (!super.getSnapshots().isEmpty()) {
                if (super.getItemCount() > 0) {
                    try {
                        super.onChildChanged(type, snapshot, newIndex, oldIndex);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        stopListening();
    }

}
