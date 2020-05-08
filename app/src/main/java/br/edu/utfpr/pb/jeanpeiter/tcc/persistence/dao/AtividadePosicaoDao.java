package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadePosicaoDTO;

@Dao
public interface AtividadePosicaoDao {

    @Query("SELECT * FROM atividade_posicao WHERE atividadeId = :atividadeId ORDER BY ordem asc")
    AtividadePosicaoDTO[] findByAtividade(long atividadeId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void save(AtividadePosicaoDTO dto);

    @Query("DELETE FROM atividade_posicao WHERE atividadeId = :atividadeId")
    void delete(long atividadeId);


}
