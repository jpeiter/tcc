package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;

@Dao
public interface AtividadeDao {

    @Query("SELECT * FROM atividade WHERE inicio BETWEEN :inicio AND :termino")
    AtividadeDTO findByInicioBetween(long inicio, long termino);

    @Insert
    void save(AtividadeDTO atividade);

    @Delete
    void delete(AtividadeDTO Atividade);


}
