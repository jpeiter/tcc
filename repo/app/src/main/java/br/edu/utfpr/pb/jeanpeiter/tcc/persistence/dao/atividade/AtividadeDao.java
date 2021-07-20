package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.atividade;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.SkipQueryVerification;
import androidx.room.Transaction;

import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadePosicaoDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.HistoricoAtividadesDto;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;

@Dao
public abstract class AtividadeDao {

    //  SELECT
    @Query(AtividadeQueries.ATIVIDADES_RESUMO)
    public abstract List<Atividade> resumos(String uid);

    @Query(AtividadeQueries.HISTORICO)
    public abstract HistoricoAtividadesDto historico(String uid);

    @Query(AtividadeQueries.QTDE_EM_DUPLA)
    public abstract long percursosEmDupla(String uid);

    @Query(AtividadeQueries.ATIVIDADE_DETALHES)
    public abstract Atividade atividade(String id);

    @Query(AtividadeQueries.POSICOES_ATIVIDADE)
    public abstract List<AtividadePosicao> posicoes(String atividadeId);

    public Atividade atividadeDetalhes(String id) {
        Atividade atividade = atividade(id);
        List<AtividadePosicao> posicoes = posicoes(id);
        atividade.setPosicoes(posicoes);
        return atividade;
    }

    //  INSERT
    @Transaction
    public void save(AtividadeDTO atividade, List<AtividadePosicaoDTO> posicoes) {
        saveAtividade(atividade);
        savePosicoes(posicoes);
    }

    @Insert
    public abstract void saveAtividade(AtividadeDTO atividade);

    @Insert
    public abstract void savePosicoes(List<AtividadePosicaoDTO> posicoes);


}
