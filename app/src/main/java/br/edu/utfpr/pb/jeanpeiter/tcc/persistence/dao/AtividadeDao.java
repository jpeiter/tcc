package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.HistoricoAtividadesDto;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.resumo.AtividadeResumo;

@Dao
public interface AtividadeDao {

    @Query(AtividadeQueries.ATIVIDADES_RESUMO)
    List<Atividade> findByInicioBetween(long inicio, long termino);

    @Insert
    void save(AtividadeDTO atividade);

    @Query("DELETE FROM atividade WHERE _id = :atividadeId")
    void delete(String atividadeId);

    @Query(AtividadeQueries.HISTORICO)
    HistoricoAtividadesDto historico(String uid);

    @Query(AtividadeQueries.QTDE_EM_DUPLA)
    long percursosEmDupla(String uid);

}
