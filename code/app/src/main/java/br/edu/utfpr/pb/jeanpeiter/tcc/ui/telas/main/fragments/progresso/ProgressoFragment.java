package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.progresso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.nivelprogresso.NivelProgressoController;
import br.edu.utfpr.pb.jeanpeiter.tcc.helper.NivelProgressoHelper;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.NivelProgresso;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.UsuarioNivelProgresso;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences.AppSharedPreferences;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.fragment.StaticFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.progresso.adapter.NivelProgressoAdapter;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.DateUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.ResourcesUtils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProgressoFragment extends Fragment implements GenericActivity {

    private View parent;

    private TextView tvNumeroNivelAtual;
    private TextView tvNomeNivelAtual;
    private TextView tvDataHoraNivelAtual;
    private TextView tvPontosAtualVsProximo;
    private ProgressBar pbProgressoNivel;
    private RecyclerView rvHistorico;

    private LinearLayout llInfos;
    private FrameLayout flSemProgresso;

    private NivelProgressoAdapter adapter;
    private ResourcesUtils resourceUtils;
    private NivelProgressoController nivelProgressoController;
    private NivelProgressoHelper nivelProgressoHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_progresso, container, false);
        resourceUtils = new ResourcesUtils(getContext());
        nivelProgressoController = new NivelProgressoController(getContext());
        nivelProgressoHelper = new NivelProgressoHelper();
        initViews();
        Usuario usuario = new AppSharedPreferences(getContext()).getUsuario();
        buscarDados(usuario.getUid());
        return parent;
    }

    @Override
    public void initViews() {
        setTvNumeroNivelAtual(parent.findViewById(R.id.tv_numero_nivel_atual));
        setTvNomeNivelAtual(parent.findViewById(R.id.tv_nome_nivel_atual));
        setTvDataHoraNivelAtual(parent.findViewById(R.id.tv_datahora_nivel_atual));
        setTvPontosAtualVsProximo(parent.findViewById(R.id.tv_pontos_atual_vs_proximo));
        setPbProgressoNivel(parent.findViewById(R.id.pb_progresso_nivel));
        setRvHistorico(parent.findViewById(R.id.rv_progresso));

        setLlInfos(parent.findViewById(R.id.ll_infos_nivel_atual));
        setFlSemProgresso(parent.findViewById(R.id.fl_sem_progresso));

        adapter = new NivelProgressoAdapter(getContext());
        getRvHistorico().setHasFixedSize(false);
        getRvHistorico().setLayoutManager(new LinearLayoutManager(getContext()));
        getRvHistorico().setAdapter(adapter);


        getPbProgressoNivel().setIndeterminateDrawable(Objects.requireNonNull(getContext()).getDrawable(R.drawable.progressbar));
        getPbProgressoNivel().setIndeterminate(true);
    }


    private void buscarDados(String uid) {
        getFlSemProgresso().setVisibility(View.VISIBLE);
        new FragmentUtils().loadFragment(getActivity(), R.id.fl_sem_progresso, new StaticFragment(R.layout.fragment_carregando_dados));

        AsyncTask.execute(() -> {
            List<UsuarioNivelProgresso> historico = nivelProgressoController.historico(uid);

            if (!historico.isEmpty()) {
                UsuarioNivelProgresso primeiro = historico.remove(0);
                getActivity().runOnUiThread(() -> {
                    adapter.setItems(historico);
                    this.atualizarInfos(primeiro);
                    getLlInfos().setVisibility(View.VISIBLE);
                    getFlSemProgresso().setVisibility(View.GONE);
                });
            } else {
                new FragmentUtils().loadFragment(getActivity(), R.id.fl_sem_progresso, new StaticFragment(R.layout.fragment_sem_progresso));
            }
        });
    }

    private void atualizarInfos(UsuarioNivelProgresso usuarioNivelProgresso) {
        int pontuacao = usuarioNivelProgresso.getPontuacao();
        NivelProgresso nivelAtual = usuarioNivelProgresso.getNivel();

        String pontosAtualVsProximo = resourceUtils.replace(R.string.pontos_atual_vs_proximo, String.valueOf(pontuacao), String.valueOf(nivelAtual.getPontuacaoMaxima()));

        getTvNumeroNivelAtual().setText(String.valueOf(nivelAtual.getOrdem()));
        getTvNomeNivelAtual().setText(nivelAtual.getDescricaoId());

        getTvDataHoraNivelAtual().setText(new DateUtils().diaSemanaDataEHora(usuarioNivelProgresso.getDataHora()));
        getTvPontosAtualVsProximo().setText(pontosAtualVsProximo);

        getPbProgressoNivel().setIndeterminate(false);
        getPbProgressoNivel().setMin(nivelAtual.getPontuacaoMinima());
        getPbProgressoNivel().setMax(nivelAtual.getPontuacaoMaxima());
        getPbProgressoNivel().setProgress(pontuacao);

    }

}
