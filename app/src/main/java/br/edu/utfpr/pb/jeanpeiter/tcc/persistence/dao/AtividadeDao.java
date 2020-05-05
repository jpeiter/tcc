package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;

@Dao
public interface AtividadeDao {

    @Insert
    void insert(Atividade atviAtividade);

    @Delete
    void delete(Atividade atividade);

    @Query("SELECT * FROM atividade WHERE _id = :_id")
    Atividade findById(Long _id);


}
