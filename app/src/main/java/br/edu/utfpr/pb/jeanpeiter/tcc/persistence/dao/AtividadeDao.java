package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;

@Dao
public interface AtividadeDao {

    @Query("SELECT * FROM atividade WHERE inicio BETWEEN :inicio AND :termino")
    AtividadeDTO[] findByInicioBetween(long inicio, long termino);

    @Insert
    void save(AtividadeDTO atividade);

    @Query("DELETE FROM atividade WHERE _id = :atividadeId")
    void delete(String atividadeId);


}
