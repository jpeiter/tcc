package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.progresso.detalhes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Optional;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeDetalhesResourceController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.atividade.AtividadeDatabase;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.fragment.StaticFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.maps.StaticMapaFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.DateUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.ResourcesUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
public class AtividadeHistoricoDetalheActivity extends AppCompatActivity implements GenericActivity {

    public static final String ATIVIDADE_ID_EXTRA = "atividadeId";

    private ConstraintLayout clPrincipal;
    private FrameLayout flSemAtividades;
    private TextView tvData;
    private TextView tvPercursoAoLado;
    private TextView tvDistancia;
    private TextView tvUnidadeDistancia;
    private TextView tvDuracao;
    private TextView tvUnidadeVelocidade;
    private TextView tvCalorias;
    private TextView tvInicio;
    private TextView tvFim;
    private TextView tvVelocidade;
    private TextView tvRitmo;
    private FrameLayout flMapaAtividade;
    private StaticMapaFragment mapFragment = new StaticMapaFragment();

    private DateUtils dateUtils;

    private AtividadeDetalhesResourceController resourceController;
    private Atividade atividade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_historico_detalhe);
        resourceController = new AtividadeDetalhesResourceController(this);
        dateUtils = new DateUtils();
        initViews();
        String atividadeId = getIntent().getStringExtra(ATIVIDADE_ID_EXTRA);
        buscarDados(atividadeId);
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

    public void btnVoltarClick(View view) {
        this.finish();
    }

    @Override
    public void initViews() {
        setClPrincipal(findViewById(R.id.cl_principal_atividade_historico_detalhe));
        setFlSemAtividades(findViewById(R.id.fl_sem_atividades));
        FrameLayout container = findViewById(R.id.fl_atividade_historico_detalhe);

        setTvData(findViewById(R.id.tv_data_atividade_historico_detalhe));
        setTvPercursoAoLado(container.findViewById(R.id.tv_percurso_ao_lado_atividade_historico_detalhe));

        setTvDistancia(container.findViewById(R.id.tv_distancia_atividade_historico_detalhe));
        setTvUnidadeDistancia(container.findViewById(R.id.tv_unidade_medida_distancia_atividade_historico_detalhe));

        setTvDuracao(container.findViewById(R.id.tv_duracao_atividade_historico_detalhe));
        setTvUnidadeVelocidade(container.findViewById(R.id.tv_unidade_medida_velocidade_atividade_historico_detalhe));

        setTvCalorias(container.findViewById(R.id.tv_calorias_atividade_historico_detalhe));
        setTvInicio(container.findViewById(R.id.tv_inicio_atividade_historico_detalhe));
        setTvFim(container.findViewById(R.id.tv_fim_atividade_historico_detalhe));
        setTvVelocidade(container.findViewById(R.id.tv_velocidade_media_atividade_historico_detalhe));
        setTvRitmo(container.findViewById(R.id.tv_ritmo_atividade_historico_detalhe));
        setFlMapaAtividade(findViewById(R.id.fl_mapa_atividade_historico_detalhe_atividade_historico_detalhe));

    }

    private void buscarDados(String atividadeId) {
        new FragmentUtils().loadFragment(this, R.id.fl_sem_atividades, new StaticFragment(R.layout.fragment_carregando_dados));
        getClPrincipal().setVisibility(View.GONE);
        getFlSemAtividades().setVisibility(View.VISIBLE);

        AtividadeDatabase db = new AtividadeDatabase(this);
        AsyncTask.execute(() -> {
            this.atividade = db.detalhes(atividadeId);
            atualizarDados(atividade);
            getClPrincipal().setVisibility(View.VISIBLE);
            getFlSemAtividades().setVisibility(View.GONE);
        });
    }

    private void atualizarDados(Atividade atividade) {
        runOnUiThread(() -> {
            atualizarDadosUi(atividade);
        });
    }

    private void atualizarDadosUi(Atividade atividade) {
        new FragmentUtils().loadFragment(this, R.id.fl_mapa_atividade_historico_detalhe_atividade_historico_detalhe, mapFragment);
        getTvData().setText(resourceController.diaSemanaEData(atividade.getInicio()));
        getTvPercursoAoLado().setVisibility(atividade.getParceiroNome() != null ? View.VISIBLE : View.INVISIBLE);
        if (atividade.getParceiroNome() != null) {
            String nomeParceiro = Optional.ofNullable(atividade.getParceiroNome()).orElse("");
            String txt = new ResourcesUtils(this).replace(R.string.percurso_ao_lado_de, nomeParceiro);
            getTvPercursoAoLado().setText(txt);
        }

        getTvDistancia().setText(resourceController.distancia(atividade.getDistancia()));
        getTvUnidadeDistancia().setText(resourceController.unidadeMedidaDistancia(atividade.getDistancia()));

        getTvDuracao().setText(resourceController.duracao(atividade.getDuracao()));

        getTvCalorias().setText(resourceController.calorias(atividade.getCalorias()));
        getTvInicio().setText(resourceController.inicio(atividade.getInicio()));
        getTvFim().setText(resourceController.fim(atividade.getTermino()));
        getTvVelocidade().setText(resourceController.velocidade(atividade.getVelocidade()));
        getTvRitmo().setText(resourceController.ritmo(atividade.getRitmo()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMapReady(Boolean ready) {
        EventBus.getDefault().post(atividade.getPosicoes());
    }

}