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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeEstadoSingleton;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeResourceController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ListenerActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.modelo.AtividadeActivityBundle;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.modelo.AtividadeFragmentBundle;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.maps.MapaFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.AnimationUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeFragment extends Fragment implements GenericActivity, ListenerActivity {

    private View parent;
    private AtividadeResourceController resourceController;

    // Imutáveis
    private TextView tvAtividadePausada;
    private MaterialButton btnPausarParar;
    private MaterialButton btnRetomarAtividade;
    private FrameLayout frameLayoutRetomarAtividade;
    private FrameLayout flMapaAtividade;

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
        loadFragment(new MapaFragment());
        initListeners();
        tempoDecorrido = SystemClock.elapsedRealtime() - cronometroDuracao.getBase();
        cronometroDuracao.start();
        return parent;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void initViews() {
        // Imutáveis
        setBtnPausarParar(parent.findViewById(R.id.btn_pausar_parar_atividade));
        setBtnRetomarAtividade(parent.findViewById(R.id.btn_retomar_atividade));
        setFrameLayoutRetomarAtividade(parent.findViewById(R.id.fl_btn_retomar_atividade));
        setTvAtividadePausada(parent.findViewById(R.id.tv_atividade_pausada));
        getTvAtividadePausada().setVisibility(View.GONE);
        setFlMapaAtividade(parent.findViewById(R.id.flMapAtividade));

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

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flMapAtividade, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void initListeners() {
        getBtnPausarParar().setOnClickListener(v -> {
            if (AtividadeEstadoSingleton.getInstance().isEmAndamento()) {
                pausarAtividade();
            } else {
                Toast.makeText(getContext(), getString(R.string.mantenha_pressionado_para_finalizar), Toast.LENGTH_SHORT).show();
            }
        });

        getBtnPausarParar().setOnLongClickListener(v -> {
            if (AtividadeEstadoSingleton.getInstance().isPausada()) {
                finalizarAtividade(System.currentTimeMillis());
            } else {
                Toast.makeText(getContext(), getString(R.string.pressione_para_finalizar), Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        getBtnRetomarAtividade().setOnClickListener(v -> retomarAtividade());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(AtividadeFragmentBundle bundle) {
        switch (bundle.getMetodo()) {
            case ATUALIZAR:
                this.atualizar(bundle.getAtividade());
                break;
            case PAUSAR:
                this.pausarAtividade();
                break;
            case RETOMAR:
                this.retomarAtividade();
                break;
        }
    }


    private void pausarAtividade() {
        pausaAtividadeUi();
        tempoDecorrido = SystemClock.elapsedRealtime() - cronometroDuracao.getBase();
        cronometroDuracao.stop();
        updateActivity(new AtividadeActivityBundle(AtividadeActivityBundle.AtividadeActivityMetodo.PAUSAR));
    }

    private void pausaAtividadeUi() {
        getBtnPausarParar().setIcon(AppCompatResources.getDrawable(getContext(), R.drawable.ic_stop_white_36dp));
        getFrameLayoutRetomarAtividade().setVisibility(View.VISIBLE);
        getTvAtividadePausada().setVisibility(View.VISIBLE);
        getTvAtividadePausada().startAnimation(new AnimationUtils().piscar());
    }

    private void retomarAtividade() {
        retomarAtividadeUi();
        cronometroDuracao.setBase(SystemClock.elapsedRealtime() - tempoDecorrido);
        cronometroDuracao.start();
        updateActivity(new AtividadeActivityBundle(AtividadeActivityBundle.AtividadeActivityMetodo.RETOMAR));
    }

    private void retomarAtividadeUi() {
        getTvAtividadePausada().setVisibility(View.GONE);
        getFrameLayoutRetomarAtividade().setVisibility(View.GONE);
        getTvAtividadePausada().clearAnimation();
        getBtnPausarParar().setIcon(AppCompatResources.getDrawable(getContext(), R.drawable.ic_pause_white_36dp));
    }

    private void finalizarAtividade(long termino) {
        updateActivity(
                new AtividadeActivityBundle(
                        AtividadeActivityBundle.AtividadeActivityMetodo.FINALIZAR,
                        tempoDecorrido,
                        termino)
        );
    }

    private void atualizar(Atividade atividade) {
        getActivity().runOnUiThread(() -> {
            long tempoDecorrido = SystemClock.elapsedRealtime() - cronometroDuracao.getBase();
            getTvDistancia().setText(String.valueOf(resourceController.distancia(atividade.getDistancia())));
            getTvUnidadeDistancia().setText(resourceController.getUnidadeMedidaDistancia(atividade.getDistancia()));
            getTvVelocidade().setText(String.valueOf(resourceController.velocidade(atividade.getDistancia(), tempoDecorrido)));
            getTvRitmo().setText(String.valueOf(resourceController.ritmo(atividade.getDistancia(), tempoDecorrido)));
            getTvCalorias().setText(String.valueOf(resourceController.calorias(atividade.getDistancia(), tempoDecorrido)));
        });
    }

    private void updateActivity(AtividadeActivityBundle bundle) {
        EventBus.getDefault().post(bundle);
    }
}

