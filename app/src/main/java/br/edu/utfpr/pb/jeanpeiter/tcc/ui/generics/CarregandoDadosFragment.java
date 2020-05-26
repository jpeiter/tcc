package br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;

public class CarregandoDadosFragment extends Fragment {

    private View parent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_carregando_dados, container, false);
        return parent;
    }
}
