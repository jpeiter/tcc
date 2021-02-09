package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.AtividadeHistoricoResumo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.AtividadeHistoricoResumoDto;
import lombok.Getter;
import lombok.Setter;

@Dao
public interface AtividadeDao {

    @Query("SELECT * FROM atividade WHERE inicio BETWEEN :inicio AND :termino")
    AtividadeDTO[] findByInicioBetween(long inicio, long termino);

    @Insert
    void save(AtividadeDTO atividade);

    @Query("DELETE FROM atividade WHERE _id = :atividadeId")
    void delete(String atividadeId);

    @Query(AtividadeQueries.HISTORICO)
    AtividadeHistoricoResumoDto historico(String uid);

    @Query(AtividadeQueries.QTDE_EM_DUPLA)
    long percursosEmDupla(String uid);

}
