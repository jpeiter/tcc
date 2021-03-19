package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.atividade;

import android.graphics.Region;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;

import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadePosicaoDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.HistoricoAtividadesDto;

@Dao
public abstract class AtividadeDao {

    //    SELECT
    @Query(AtividadeQueries.ATIVIDADES_RESUMO)
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    public abstract List<Atividade> findByInicioBetween(long inicio, long termino);

    @Query(AtividadeQueries.HISTORICO)
    public abstract HistoricoAtividadesDto historico(String uid);

    @Query(AtividadeQueries.QTDE_EM_DUPLA)
    public abstract long percursosEmDupla(String uid);

    //    INSERT
    @Transaction
    public void save(AtividadeDTO atividade, List<AtividadePosicaoDTO> posicoes) {
        saveAtividade(atividade);
        savePosicoes(posicoes);
    }

    @Insert
    public abstract void saveAtividade(AtividadeDTO atividade);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void savePosicoes(List<AtividadePosicaoDTO> posicoes);

    // DELETE
    @Transaction
    public void delete(String atividadeId) {
        deleteAtividade(atividadeId);
        deletePosicoes(atividadeId);
    }

    @Query("DELETE FROM atividade WHERE _id = :atividadeId")
    abstract void deleteAtividade(String atividadeId);

    @Query("DELETE FROM atividade_posicao WHERE atividadeId = :atividadeId")
    abstract void deletePosicoes(String atividadeId);


}
