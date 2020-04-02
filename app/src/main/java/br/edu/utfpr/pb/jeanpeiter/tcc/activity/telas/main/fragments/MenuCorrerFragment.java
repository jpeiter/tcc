package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.GenericActivity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class MenuCorrerFragment extends Fragment implements  GenericActivity {

    FloatingActionButton btnIniciarDupla;
    FloatingActionButton btnIniciarSozinho;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu_correr, container, false);
        initViews();
        return view;
    }

    @Override
    public void initViews() {
        setBtnIniciarDupla(view.findViewById(R.id.btnIniciarDupla));
        setBtnIniciarSozinho(view.findViewById(R.id.btnIniciarSozinho));
        getBtnIniciarSozinho().setOnClickListener(listener);
        getBtnIniciarDupla().setOnClickListener(listener);
    }

    private View.OnClickListener listener = v -> {
        String tipo = v.getId() == R.id.btnIniciarDupla ? "DUPLA" : "SOZINHO";
        Toast.makeText(v.getContext(), tipo, Toast.LENGTH_SHORT).show();
    };


}
