package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.historico.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Optional;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeResourceController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.resumo.AtividadeResumo;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.RecyclerViewOnClickInterface;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.adapter.GenericAdapter;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.ResourcesUtils;

public class AtividadeResumoAdapter extends GenericAdapter<AtividadeResumo, AtividadeResumoAdapter.AtividadeResumoViewHolder> {

    private final AtividadeResourceController resourceController;
    private final ResourcesUtils resourcesUtils;
    private final RecyclerViewOnClickInterface onClickInterface;

    public AtividadeResumoAdapter(Context context, RecyclerViewOnClickInterface onClickInterface) {
        super(context);
        resourceController = new AtividadeResourceController(context);
        resourcesUtils = new ResourcesUtils(context);
        this.onClickInterface = onClickInterface;
    }

    @Override
    public void onBindData(AtividadeResumoViewHolder holder, AtividadeResumo atividade) {
        String distancia = resourceController.distancia(atividade.getDistancia()) + resourceController.getUnidadeMedidaDistancia(atividade.getDistancia());
        String data = resourceController.diaSemanaEData(atividade.getInicio().toLocalDate());
        String tempo = resourceController.tempo(atividade.getDuracao());
        String nomeParceiro = Optional.ofNullable(atividade.getParceiroNome())
                .map(nome -> nome.split(" ")[0])
                .orElse("");
        holder.tvData.setText(data);
        holder.tvDistancia.setText(distancia);
        holder.tvTempo.setText(tempo);
        holder.tvPontos.setText(resourcesUtils.replace(R.string.x_xp, String.valueOf(atividade.getPontos())));

        holder.tvNomeParceiro.setText(nomeParceiro);
        holder.tvNomeParceiro.setVisibility(nomeParceiro.isEmpty() ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public AtividadeResumoViewHolder setViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atividade_historico_item, parent, false);
        return new AtividadeResumoViewHolder(itemView);
    }

    class AtividadeResumoViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvData;
        private final TextView tvNomeParceiro;
        private final TextView tvDistancia;
        private final TextView tvTempo;
        private final TextView tvPontos;

        public AtividadeResumoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tv_atividade_resumo_data);
            tvNomeParceiro = itemView.findViewById(R.id.tv_atividade_resumo_nome_parceiro);
            tvDistancia = itemView.findViewById(R.id.tv_atividade_resumo_distancia);
            tvTempo = itemView.findViewById(R.id.tv_atividade_resumo_tempo);
            tvPontos = itemView.findViewById(R.id.tv_atividade_resumo_pontos);
            itemView.setOnClickListener(v ->
                    AtividadeResumoAdapter.this.onClickInterface.onItemClick(getAdapterPosition())
            );
        }
    }
}
