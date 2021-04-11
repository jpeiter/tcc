package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.historico;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.fragment.StaticFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.progresso.detalhes.AtividadeHistoricoDetalheActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeResourceController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.atividade.AtividadeDatabase;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.HistoricoAtividades;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.resumo.AtividadeResumo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences.AppSharedPreferences;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.RecyclerViewOnClickInterface;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ResourceActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.historico.adapter.AtividadeResumoAdapter;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.ResourcesUtils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HistoricoFragment extends Fragment implements GenericActivity, ResourceActivity, RecyclerViewOnClickInterface {

    public static final int REQUEST_CODE = 1001;

    private TextView tvVocePercorreu;
    private TextView tvDistanciaTotal;
    private TextView tvUnidadeDistanciaTotal;
    private TextView tvTempoTotal;
    private TextView tvPercursosDupla;
    private TextView tvTotalPercursosDeAte;
    private RecyclerView rvHistorico;
    private AtividadeResumoAdapter adapter;
    private View parent;

    private ConstraintLayout clPrincipal;
    private FrameLayout flSemHistorico;

    private ResourcesUtils resourcesUtils;
    private AtividadeResourceController resourceController;
    private List<AtividadeResumo> resumos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_historico, container, false);
        resourceController = new AtividadeResourceController(getContext());
        resourcesUtils = new ResourcesUtils(getContext());
        initViews();
        replaceResources();
        buscarDados();
        return parent;
    }

    @Override
    public void initViews() {
        setClPrincipal(parent.findViewById(R.id.cl_principal_historico));
        setFlSemHistorico(parent.findViewById(R.id.fl_sem_historico));
        setTvVocePercorreu(parent.findViewById(R.id.tv_voce_percorreu_historico));
        setTvDistanciaTotal(parent.findViewById(R.id.tv_distancia_total_historico));
        setTvUnidadeDistanciaTotal(parent.findViewById(R.id.tv_unidade_medida_distancia_total_historico));
        setTvTempoTotal(parent.findViewById(R.id.tv_tempo_movimento_total_historico));
        setTvPercursosDupla(parent.findViewById(R.id.tv_percursos_em_dupla_historico));
        setTvTotalPercursosDeAte(parent.findViewById(R.id.tv_total_percursos_de_ate));
        setRvHistorico(parent.findViewById(R.id.rv_historico));
        adapter = new AtividadeResumoAdapter(getContext(), this);
        getRvHistorico().setHasFixedSize(false);
        getRvHistorico().setLayoutManager(new LinearLayoutManager(getContext()));
        getRvHistorico().setAdapter(adapter);

    }

    @Override
    public void replaceResources() {
        Usuario usuario = new AppSharedPreferences(getContext()).getUsuario();
        String nome = usuario.getNome().split(" ")[0];
        getTvVocePercorreu().setText(resourcesUtils.replace(R.string.x_voce_ja_correu, nome));
    }

    private void buscarDados() {
        Usuario usuario = new AppSharedPreferences(getContext()).getUsuario();
        String uid = usuario.getUid();
        getFlSemHistorico().setVisibility(View.VISIBLE);
        getClPrincipal().setVisibility(View.GONE);
        new FragmentUtils().loadFragment(getActivity(), R.id.fl_sem_historico, new StaticFragment(R.layout.fragment_carregando_dados));
        AtividadeDatabase atividadeDatabase = new AtividadeDatabase(getContext());
        AsyncTask.execute(() -> {
            HistoricoAtividades historico = atividadeDatabase.getHistorico(uid);
            if (historico != null) {
                long percursosEmDupla = atividadeDatabase.percursosEmDupla(uid);
                this.resumos = atividadeDatabase.resumos(uid);
                atualizarDados(historico, percursosEmDupla, this.resumos);
            } else {
                new FragmentUtils().loadFragment(getActivity(), R.id.fl_sem_historico, new StaticFragment(R.layout.fragment_sem_progresso));
            }
        });
    }

    private void atualizarDados(HistoricoAtividades historico, long percursosEmDupla, List<AtividadeResumo> resumos) {
        getActivity().runOnUiThread(() -> {
            atualizarDadosUi(historico, percursosEmDupla, resumos);
            getFlSemHistorico().setVisibility(View.GONE);
            getClPrincipal().setVisibility(View.VISIBLE);
        });

    }

    private void atualizarDadosUi(HistoricoAtividades historico, long percursosEmDupla, List<AtividadeResumo> resumos) {
        setHistorico(historico);
        setPercursosEmDupla(percursosEmDupla);
        adapter.setItems(resumos);
    }

    private void setHistorico(HistoricoAtividades resumo) {
        getTvDistanciaTotal().setText(resourceController.distancia(resumo.getDistanciaTotal()));
        getTvUnidadeDistanciaTotal().setText(resourceController.getUnidadeMedidaDistancia(resumo.getDistanciaTotal()));
        getTvTempoTotal().setText(resourceController.tempo(resumo.getTempoMovimento()));
        getTvTotalPercursosDeAte().setText(
                resourcesUtils.replace(
                        R.string.x_percursos_de_ate,
                        String.valueOf(resumo.getTotalPercursos()),
                        resourceController.data(resumo.getDataDe()),
                        resourceController.data(resumo.getDataAte())
                )
        );
    }

    private void setPercursosEmDupla(long percursosEmDupla) {
        getTvPercursosDupla().setText(String.valueOf(percursosEmDupla));
    }

    @Override
    public void onItemClick(int position) {
        Context context = getContext();
        Intent i = new Intent(context, AtividadeHistoricoDetalheActivity.class);
        AtividadeResumo atividade = resumos.get(position);
        i.putExtra(AtividadeHistoricoDetalheActivity.ATIVIDADE_ID_EXTRA, atividade.get_id());
        startActivity(i);
    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            buscarDados();
//        }
//    }

}
