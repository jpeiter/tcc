package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.dupla;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.connectivity.info.NetworkInformation;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseUserStatusController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.FirebaseUserStatus;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.CarregandoDadosFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ListenerActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;

public class SelecionarParceiroActivity extends AppCompatActivity implements ListenerActivity {

    private Query queryUsuariosConectados = FirebaseUserStatusController.usuariosConectados();
    private final ValueEventListener listenerUsuariosConectados = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<FirebaseUserStatus> userStatuses = new ArrayList<>();
            dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                userStatuses.add(dataSnapshot1.getValue(FirebaseUserStatus.class));
            });

            userStatuses.size();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_parceiro);

        if (NetworkInformation.isNetworkAvailable(this)) {
            FirebaseUserStatusController.getDatabase().getDatabase().goOnline();
            new FragmentUtils().loadFragment(this, R.id.fl_selecionar_parceiro, new CarregandoDadosFragment());
            buscarUsuarios();
            FirebaseUserStatusController.desconectar(this).addOnSuccessListener(success -> {
                Toast.makeText(this, "DESCONECTOU!", Toast.LENGTH_SHORT).show();
            });

        }
    }

    @Override
    public void finish() {
        super.finish();
        if (NetworkInformation.isNetworkAvailable(this)) {
            FirebaseUserStatusController.getDatabase().getDatabase().goOffline();
        }
    }

    private void buscarUsuarios() {
        FirebaseUserStatusController.conectar(this).addOnSuccessListener(success -> {
            queryUsuariosConectados.addValueEventListener(listenerUsuariosConectados);
        });
    }

    @Override
    public void initListeners() {
        if (NetworkInformation.isNetworkAvailable(this)) {

        }
    }
}
