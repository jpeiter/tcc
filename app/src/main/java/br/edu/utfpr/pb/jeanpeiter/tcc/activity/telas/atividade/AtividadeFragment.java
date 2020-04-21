package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.atividade;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.ResourceActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.ResourcesUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeFragment extends Fragment implements ResourceActivity {

    private View parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_atividade, container, false);

        replaceResources();
        return parent;
    }

    @Override
    public void replaceResources() {
        ResourcesUtils resourcesUtils = new ResourcesUtils(getContext());
    }
}

