package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.atividade;

import android.os.Bundle;
import android.os.VibrationEffect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.ListenerActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeViewController;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.enums.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.AnimationUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeFragment extends Fragment implements ListenerActivity {

    private View parent;
    private AtividadeActivity atividadeActivity;
    private AtividadeViewController viewController;

    // Imutáveis
    private TextView tvAtividadePausada;
    private MaterialButton btnPausarParar;
    private MaterialButton btnRetomarAtividade;
    private FrameLayout frameLayoutRetomarAtividade;

    // Valores
    private TextView tvDistancia;
    private TextView tvDuracao;
    private TextView tvVelocidade;
    private TextView tvRitmo;
    private TextView tvCalorias;

    // Unidades de medida
    private TextView tvUnidadeDistancia;
    private TextView tvUnidadeVelocidade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_atividade, container, false);
        atividadeActivity = (AtividadeActivity) getActivity();

        // Imutáveis
        setBtnPausarParar(parent.findViewById(R.id.btn_pausar_parar_atividade));
        setBtnRetomarAtividade(parent.findViewById(R.id.btn_retomar_atividade));
        setFrameLayoutRetomarAtividade(parent.findViewById(R.id.fl_btn_retomar_atividade));
        setTvAtividadePausada(parent.findViewById(R.id.tv_atividade_pausada));
        getTvAtividadePausada().setVisibility(View.GONE);

        // Valores
        setTvDistancia(parent.findViewById(R.id.tv_distancia_atividade));
        setTvDuracao(parent.findViewById(R.id.tv_duracao_atividade));
        setTvVelocidade(parent.findViewById(R.id.tv_velocidade_media_atividade));
        setTvRitmo(parent.findViewById(R.id.tv_ritmo_medio_atividade));
        setTvCalorias(parent.findViewById(R.id.tv_calorias_atividade));

        // Unidades de medida
        setTvUnidadeDistancia(parent.findViewById(R.id.tv_unidade_medida_distancia_atividade));
        setTvUnidadeVelocidade(parent.findViewById(R.id.tv_unidade_medida_velocidade_atividade));

        viewController = new AtividadeViewController();

        initListeners();
        return parent;
    }

    @Override
    public void initListeners() {
        getBtnPausarParar().setOnClickListener(v -> {
            if (atividadeActivity.getAtividadeEstado().equals(AtividadeEstado.EM_ANDAMENTO)) {
                getFrameLayoutRetomarAtividade().setVisibility(View.VISIBLE);
                pausarAtividade();
            } else {
                VibrationEffect.createOneShot(VibrationEffect.EFFECT_HEAVY_CLICK, VibrationEffect.EFFECT_HEAVY_CLICK);
                Toast.makeText(getContext(), getString(R.string.mantenha_pressionado_para_finalizar), Toast.LENGTH_SHORT).show();
            }
        });

        getBtnPausarParar().setOnLongClickListener(v -> {
            if (atividadeActivity.getAtividadeEstado().equals(AtividadeEstado.PAUSADA)) {
                finalizarAtividade();
            } else {
                Toast.makeText(getContext(), getString(R.string.pressione_para_finalizar), Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        getBtnRetomarAtividade().setOnClickListener(v -> retomarAtividade());
    }


    private void retomarAtividade() {
        retomarAtividadeUi();
        atividadeActivity.retomarAtividade();
    }

    private void retomarAtividadeUi() {
        getTvAtividadePausada().setVisibility(View.GONE);
        getFrameLayoutRetomarAtividade().setVisibility(View.GONE);
        getTvAtividadePausada().clearAnimation();
        getBtnPausarParar().setIcon(AppCompatResources.getDrawable(getContext(), R.drawable.ic_pause_white_36dp));
    }

    private void pausarAtividade() {
        pausaAtividadeUi();
        atividadeActivity.pausarAtividade();
    }

    protected void pausaAtividadeUi() {
        getBtnPausarParar().setIcon(AppCompatResources.getDrawable(getContext(), R.drawable.ic_stop_white_36dp));
        getFrameLayoutRetomarAtividade().setVisibility(View.VISIBLE);
        getTvAtividadePausada().setVisibility(View.VISIBLE);
        getTvAtividadePausada().startAnimation(new AnimationUtils().piscar());
    }

    private void finalizarAtividade() {
        atividadeActivity.finalizarAtividade();
    }

    protected void atualizar(Atividade atividade) {
        viewController.atualizarValor(getTvDistancia(), atividade.getDistancia());
        viewController.atualizarValor(getTvVelocidade(), atividade.getVelocidade());
    }
}

