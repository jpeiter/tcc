package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.digidemic.unitof.UnitOf;

import java.math.BigDecimal;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeResourceController;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseUserController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.AtividadeDao;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.AppDatabase;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.AtividadeHistoricoResumo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences.AppSharedPreferences;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.ResourceActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.BigDecimalUtils;
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

    private View parent;

    private AppDatabase db;

    private ResourcesUtils resourcesUtils;
    private AtividadeResourceController resourceController;
    private UnitOf.Time conversorTempo = new UnitOf.Time();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_historico, container, false);
        db = AppDatabase.getInstance(getContext());
        resourceController = new AtividadeResourceController(getContext());
        resourcesUtils = new ResourcesUtils(getContext());
        initViews();
        replaceResources();
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
    }

    @Override
    public void replaceResources() {
        Usuario usuario = new AppSharedPreferences(getContext()).getUsuario();
        getTvVocePercorreu().setText(resourcesUtils.replace(R.string.x_voce_ja_correu, usuario.getNome()));
        buscarDados(usuario.getUid());

    }

    private void buscarDados(String uid) {
        AsyncTask.execute(() -> {
            AtividadeHistoricoResumo resumo = AppDatabase.getInstance(getContext()).getHistorico(uid);
            long percursosEmDupla = AppDatabase.getInstance(getContext()).percursosEmDupla(uid);
            getActivity().runOnUiThread(() -> {
                setHistorico(resumo);
                setPercursosEmDupla(percursosEmDupla);
            });

        });
    }


    private void setHistorico(AtividadeHistoricoResumo resumo) {
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
