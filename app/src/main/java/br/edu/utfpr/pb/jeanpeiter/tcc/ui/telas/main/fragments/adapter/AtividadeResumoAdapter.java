package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade.AtividadeResourceController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.resumo.AtividadeResumo;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.adapter.GenericAdapter;

public class AtividadeResumoAdapter extends GenericAdapter<AtividadeResumo, AtividadeResumoAdapter.AtividadeResumoViewHolder> {

    private List<Atividade> atividades = new ArrayList<>();
    private AtividadeResourceController resourceController;

    public AtividadeResumoAdapter(Context context) {
        super(context);
        resourceController = new AtividadeResourceController(context);
    }

    @Override
    public void onBindData(AtividadeResumoViewHolder holder, AtividadeResumo atividade) {
        String distancia = resourceController.distancia(atividade.getDistancia()) + resourceController.getUnidadeMedidaDistancia(atividade.getDistancia());
        String data = resourceController.diaSemanaEData(atividade.getInicio().toLocalDate());
        String tempo = resourceController.tempo(atividade.getDuracao(), true);
        String nomeParceiro = Optional.ofNullable(atividade.getParceiroNome())
                .map(nome -> nome.split(" ")[0])
                .orElse("");
        holder.tvData.setText(data);
        holder.tvDistancia.setText(distancia);
        holder.tvTempo.setText(tempo);

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

        private TextView tvData;
        private TextView tvNomeParceiro;
        private TextView tvDistancia;
        private TextView tvTempo;

        public AtividadeResumoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tv_atividade_resumo_data);
            tvNomeParceiro = itemView.findViewById(R.id.tv_atividade_resumo_nome_parceiro);
            tvDistancia = itemView.findViewById(R.id.tv_atividade_resumo_distancia);
            tvTempo = itemView.findViewById(R.id.tv_atividade_resumo_tempo);
        }
    }
}
