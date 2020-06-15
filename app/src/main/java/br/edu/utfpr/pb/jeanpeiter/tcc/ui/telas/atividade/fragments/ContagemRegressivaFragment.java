package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ListenerActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ResourceActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.AtividadeActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.ResourcesUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContagemRegressivaFragment extends Fragment implements ResourceActivity, ListenerActivity {

    private View parent;
    private TextView tvContagemRegressiva;
    private TextView btnMaisTempo;
    private TextView btnComecarAtividade;

    private CountDownTimer cronometro;
    private Long tempo = 5L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_contagem_regressiva, container, false);
        setTvContagemRegressiva(parent.findViewById(R.id.tv_contagem_regressiva));
        setBtnMaisTempo(parent.findViewById(R.id.btn_mais_tempo_contagem_regressiva));
        setBtnComecarAtividade(parent.findViewById(R.id.btn_comecar_atividade_contagem_regressiva));

        replaceResources();
        initListeners();
        cronometro = criaCronometro(tempo);
        return parent;
    }

    @Override
    public void replaceResources() {
        ResourcesUtils resourcesUtils = new ResourcesUtils(getContext());
        getTvContagemRegressiva().setText("5");
        getBtnMaisTempo().setText(resourcesUtils.replace(R.string.mais_x_segundos, "5"));
    }

    @Override
    public void initListeners() {
        getBtnMaisTempo().setOnClickListener(v -> {
            if (cronometro != null) {
                cronometro.cancel();
            }
            tempo = tempo <= 55 ? tempo + 5 : 60;
            cronometro = criaCronometro(tempo);
        });

        getBtnComecarAtividade().setOnClickListener(v -> cronometro.onFinish());
    }

    private CountDownTimer criaCronometro(long tempoEmSegundo) {
        return new CountDownTimer((tempoEmSegundo * 1000) + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tempo = millisUntilFinished / 1000;
                getTvContagemRegressiva().setText(String.valueOf(tempo));
            }

            @Override
            public void onFinish() {
                iniciarAtividade();
            }
        }.start();
    }

    private void iniciarAtividade() {
        AtividadeActivity activity = (AtividadeActivity) getActivity();
        if (activity != null) {
            activity.iniciarAtividade();
        }
    }

}
