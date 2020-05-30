package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.dupla.fragments.selecionarparceiro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.connectivity.info.NetworkInformation;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseUserStatusController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.FirebaseUserStatus;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.adapter.FirebaseUserStatusRecyclerAdapter;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ListenerActivity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class SelecionarParceiroFragment extends Fragment implements GenericActivity, ListenerActivity {

    private View parent;
    private RecyclerView rvUsuariosConectados;

    private FirebaseRecyclerAdapter adapter;
    private FirebaseRecyclerOptions<FirebaseUserStatus> options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_selecionar_parceiro, container, false);

        initListeners();
        initViews();
        return parent;
    }


    @Override
    public void initViews() {
        final FragmentActivity activity = getActivity();
        setRvUsuariosConectados(parent.findViewById(R.id.rv_usuarios_conectados));
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        getRvUsuariosConectados().setLayoutManager(layoutManager);
        setOptions(new FirebaseRecyclerOptions.Builder<FirebaseUserStatus>().setQuery(FirebaseUserStatusController.usuariosConectados(), FirebaseUserStatus.class).build());

        new Thread(() -> activity.runOnUiThread(() -> {
            // Adapter
            setAdapter(new FirebaseUserStatusRecyclerAdapter(getOptions()));
            getRvUsuariosConectados().setAdapter(getAdapter());
        })).start();

    }

    @Override
    public void initListeners() {
        if (NetworkInformation.isNetworkAvailable(getActivity())) {
            // Firebase Database
            FirebaseUserStatusController.getDatabase().getDatabase().goOnline();
            FirebaseUserStatusController.conectar(getActivity()).addOnSuccessListener(success -> {
            });
            FirebaseUserStatusController.desconectar().addOnSuccessListener(success -> {
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (NetworkInformation.isNetworkAvailable(getActivity())) {
            FirebaseUserStatusController.getDatabase().getDatabase().goOffline();
        }
    }


}
