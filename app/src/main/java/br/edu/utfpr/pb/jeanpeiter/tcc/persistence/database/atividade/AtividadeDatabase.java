package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.atividade;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.AppDatabase;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadePosicaoDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.HistoricoAtividades;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.HistoricoAtividadesDto;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.resumo.AtividadeResumo;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.DateUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AtividadeDatabase {

    private final Context context;

    public List<AtividadeResumo> resumos(String uid) {
        DateUtils dateUtils = new DateUtils();
        List<Atividade> resumos = AppDatabase.getInstance(context).atividadeDao().resumos(uid);
        return resumos != null ? resumos.stream().map(AtividadeResumo::new).collect(Collectors.toList()) : new ArrayList<>();
    }

    public String save(Atividade atividade) throws Exception {
        String atividadeId = atividade.get_id();
        AtividadeDTO atividadeDto = atividade.toDto();
        List<AtividadePosicaoDTO> posicoesDto = atividade.getPosicoes()
                .stream().map(
                        p -> p.toDto(atividadeId)
                ).collect(Collectors.toList());

        AppDatabase.getInstance(context).atividadeDao().save(atividadeDto, posicoesDto);
        return atividadeId;
    }

    public void deleteAtividade(Atividade atividade) {
        AtividadeDTO atividadeDto = atividade.toDto();
        List<AtividadePosicaoDTO> posicoesDto = atividade.getPosicoes()
                .stream().map(
                        p -> p.toDto(atividade.get_id())
                ).collect(Collectors.toList());
        AppDatabase.getInstance(context).atividadeDao().delete(atividadeDto, posicoesDto);
    }

    public Atividade detalhes(String atividadeId) {
        return AppDatabase.getInstance(context).atividadeDao().atividadeDetalhes(atividadeId);
    }

    public HistoricoAtividades getHistorico(String uid) {
        HistoricoAtividadesDto historicoDto = AppDatabase.getInstance(context).atividadeDao().historico(uid);
        return historicoDto != null && historicoDto.getTotalPercursos() > 0 ? historicoDto.parse() : null;
    }

    public long percursosEmDupla(String uid) {
        return AppDatabase.getInstance(context).atividadeDao().percursosEmDupla(uid);
    }


}
