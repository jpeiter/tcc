package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.historico;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeResourceController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.atividade.AtividadeDatabase;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.HistoricoAtividades;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.resumo.AtividadeResumo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences.AppSharedPreferences;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ResourceActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.historico.adapter.AtividadeResumoAdapter;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.ResourcesUtils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HistoricoFragment extends Fragment implements GenericActivity, ResourceActivity {

    private TextView tvVocePercorreu;
    private TextView tvDistanciaTotal;
    private TextView tvUnidadeDistanciaTotal;
    private TextView tvTempoTotal;
    private TextView tvPercursosDupla;
    private TextView tvTotalPercursosDeAte;
    private RecyclerView rvHistorico;
    private AtividadeResumoAdapter adapter;
    private View parent;

    private ResourcesUtils resourcesUtils;
    private AtividadeResourceController resourceController;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_historico, container, false);
        resourceController = new AtividadeResourceController(getContext());
        resourcesUtils = new ResourcesUtils(getContext());
        initViews();
        replaceResources();
        Usuario usuario = new AppSharedPreferences(getContext()).getUsuario();
        buscarDados(usuario.getUid());
        return parent;
    }

    @Override
    public void initViews() {
        setTvVocePercorreu(parent.findViewById(R.id.tv_voce_percorreu_historico));
        setTvDistanciaTotal(parent.findViewById(R.id.tv_distancia_total_historico));
        setTvUnidadeDistanciaTotal(parent.findViewById(R.id.tv_unidade_medida_distancia_total_historico));
        setTvTempoTotal(parent.findViewById(R.id.tv_tempo_movimento_total_historico));
        setTvPercursosDupla(parent.findViewById(R.id.tv_percursos_em_dupla_historico));
        setTvTotalPercursosDeAte(parent.findViewById(R.id.tv_total_percursos_de_ate));
        setRvHistorico(parent.findViewById(R.id.rv_historico));
        adapter = new AtividadeResumoAdapter(getContext());
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

    private void buscarDados(String uid) {
        AsyncTask.execute(() -> {
            AtividadeDatabase atividadeDatabase = new AtividadeDatabase(getContext());
            HistoricoAtividades resumo = atividadeDatabase.getHistorico(uid);
            long percursosEmDupla = atividadeDatabase.percursosEmDupla(uid);
            getActivity().runOnUiThread(() -> {
                setHistorico(resumo);
                setPercursosEmDupla(percursosEmDupla);
            });
            buscarResumos(resumo.getDataDe(), resumo.getDataAte());
        });
    }

    private void buscarResumos(LocalDate dataDe, LocalDate dataAte) {
        AsyncTask.execute(() -> {
            List<AtividadeResumo> resumos = new AtividadeDatabase(getContext()).findByInicioBetween(dataDe, dataAte);
            getActivity().runOnUiThread(() -> adapter.setItems(resumos));
        });
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

}
