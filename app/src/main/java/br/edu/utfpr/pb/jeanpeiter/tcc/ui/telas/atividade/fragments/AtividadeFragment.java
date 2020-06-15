package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeResourceController;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeUnidadesController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ListenerActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.AtividadeActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.AnimationUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeFragment extends Fragment implements GenericActivity, ListenerActivity {

    private View parent;
    private AtividadeActivity atividadeActivity;
    private AtividadeResourceController resourceController;

    // Imutáveis
    private TextView tvAtividadePausada;
    private MaterialButton btnPausarParar;
    private MaterialButton btnRetomarAtividade;
    private FrameLayout frameLayoutRetomarAtividade;

    // Valores
    private TextView tvDistancia;
    private TextView tvVelocidade;
    private TextView tvRitmo;
    private TextView tvCalorias;
    @Setter(AccessLevel.PRIVATE)
    private Chronometer cronometroDuracao;

    // Unidades de medida
    private TextView tvUnidadeDistancia;
    private TextView tvUnidadeVelocidade;

    private long tempoDecorrido = 0L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_atividade, container, false);

        initViews();
        initListeners();

        return parent;
    }

    @Override
    public void initViews() {
        setAtividadeActivity((AtividadeActivity) getActivity());

        // Imutáveis
        setBtnPausarParar(parent.findViewById(R.id.btn_pausar_parar_atividade));
        setBtnRetomarAtividade(parent.findViewById(R.id.btn_retomar_atividade));
        setFrameLayoutRetomarAtividade(parent.findViewById(R.id.fl_btn_retomar_atividade));
        setTvAtividadePausada(parent.findViewById(R.id.tv_atividade_pausada));
        getTvAtividadePausada().setVisibility(View.GONE);

        // Valores
        setTvDistancia(parent.findViewById(R.id.tv_distancia_atividade));
        setCronometroDuracao(parent.findViewById(R.id.cronometro_duracao_atividade));
        setTvVelocidade(parent.findViewById(R.id.tv_velocidade_media_atividade));
        setTvRitmo(parent.findViewById(R.id.tv_ritmo_medio_atividade));
        setTvCalorias(parent.findViewById(R.id.tv_calorias_atividade));

        // Unidades de medida
        setTvUnidadeDistancia(parent.findViewById(R.id.tv_unidade_medida_distancia_atividade));
        setTvUnidadeVelocidade(parent.findViewById(R.id.tv_unidade_medida_velocidade_atividade));

        setResourceController(new AtividadeResourceController(getContext()));
    }

    @Override
    public void initListeners() {
        getBtnPausarParar().setOnClickListener(v -> {
            if (atividadeActivity.getAtividadeEstado().equals(AtividadeEstado.EM_ANDAMENTO)) {
                pausarAtividade();
            } else {
                Toast.makeText(getContext(), getString(R.string.mantenha_pressionado_para_finalizar), Toast.LENGTH_SHORT).show();
            }
        });

        getBtnPausarParar().setOnLongClickListener(v -> {
            if (atividadeActivity.getAtividadeEstado().equals(AtividadeEstado.PAUSADA)) {
                finalizarAtividade(System.currentTimeMillis());
            } else {
                Toast.makeText(getContext(), getString(R.string.pressione_para_finalizar), Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        getBtnRetomarAtividade().setOnClickListener(v -> retomarAtividade());

        cronometroDuracao.setOnChronometerTickListener(chronometer -> {

        });
        tempoDecorrido = SystemClock.elapsedRealtime() - cronometroDuracao.getBase();
        cronometroDuracao.start();


    }

    public void pausarAtividade() {
        pausaAtividadeUi();
        atividadeActivity.pausarAtividade();
        tempoDecorrido = SystemClock.elapsedRealtime() - cronometroDuracao.getBase();
        cronometroDuracao.stop();
    }

    private void pausaAtividadeUi() {
        getBtnPausarParar().setIcon(AppCompatResources.getDrawable(getContext(), R.drawable.ic_stop_white_36dp));
        getFrameLayoutRetomarAtividade().setVisibility(View.VISIBLE);
        getTvAtividadePausada().setVisibility(View.VISIBLE);
        getTvAtividadePausada().startAnimation(new AnimationUtils().piscar());
    }

    public void retomarAtividade() {
        retomarAtividadeUi();
        atividadeActivity.retomarAtividade();
        cronometroDuracao.setBase(SystemClock.elapsedRealtime() - tempoDecorrido);
        cronometroDuracao.start();
    }

    private void retomarAtividadeUi() {
        getTvAtividadePausada().setVisibility(View.GONE);
        getFrameLayoutRetomarAtividade().setVisibility(View.GONE);
        getTvAtividadePausada().clearAnimation();
        getBtnPausarParar().setIcon(AppCompatResources.getDrawable(getContext(), R.drawable.ic_pause_white_36dp));
    }

    private void finalizarAtividade(long termino) {
        atividadeActivity.finalizarAtividade(termino, tempoDecorrido);
    }

    public void atualizar(Atividade atividade) {
        getAtividadeActivity().runOnUiThread(() -> {
            Long tempoDecorrido = SystemClock.elapsedRealtime() - cronometroDuracao.getBase();
            getTvDistancia().setText(String.valueOf(resourceController.distancia(atividade.getDistancia())));
            getTvUnidadeDistancia().setText(resourceController.getUnidadeMedidaDistancia(atividade.getDistancia()));
            getTvVelocidade().setText(String.valueOf(resourceController.velocidade(atividade.getDistancia(), tempoDecorrido)));
            getTvRitmo().setText(String.valueOf(resourceController.ritmo(atividade.getDistancia(), tempoDecorrido)));
        });
    }
}

