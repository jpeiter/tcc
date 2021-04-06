package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.progresso.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.NivelProgresso;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.UsuarioNivelProgresso;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.adapter.GenericAdapter;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.DateUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.ResourcesUtils;

public class NivelProgressoAdapter extends GenericAdapter<UsuarioNivelProgresso, NivelProgressoAdapter.ProgressoAdapterViewHolder> {

    private ResourcesUtils resourcesUtils;
    private DateUtils dateUtils;

    public NivelProgressoAdapter(Context context) {
        super(context);
        resourcesUtils = new ResourcesUtils(getContext());
        dateUtils = new DateUtils();
    }

    @Override
    public void onBindData(ProgressoAdapterViewHolder holder, UsuarioNivelProgresso usuarioNivelProgresso) {
        NivelProgresso nivelProgresso = usuarioNivelProgresso.getNivel();
        String descricao = String.valueOf(nivelProgresso.getOrdem()).concat(" - ").concat(getContext().getString(nivelProgresso.getDescricaoId()));
        String pontos = resourcesUtils.replace(R.string.x_xp, String.valueOf(nivelProgresso.getPontuacaoMaxima()));
        String dataHora = dateUtils.diaSemanaDataEHora(usuarioNivelProgresso.getDataHora());

        holder.tvDescricaoNivel.setText(descricao);
        holder.tvPontosNivel.setText(pontos);
        holder.tvDataHoraNivel.setText(dataHora);
    }

    @Override
    public ProgressoAdapterViewHolder setViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nivel_progresso, parent, false);
        return new ProgressoAdapterViewHolder(itemView);
    }

    class ProgressoAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDescricaoNivel;
        private TextView tvPontosNivel;
        private TextView tvDataHoraNivel;

        public ProgressoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescricaoNivel = itemView.findViewById(R.id.tv_info_nivel);
            tvPontosNivel = itemView.findViewById(R.id.tv_pontos_nivel);
            tvDataHoraNivel = itemView.findViewById(R.id.tv_data_conquista_nivel);
        }
    }
}
