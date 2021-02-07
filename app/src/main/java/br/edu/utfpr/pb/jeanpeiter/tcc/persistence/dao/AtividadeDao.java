package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
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

    @Query("SELECT SUM(distancia) as distanciaTotal, SUM(duracao) as tempoMovimento FROM atividade where userid = :uid")
    Historico historico(String uid);

    @Query("SELECT COUNT(*) FROM atividade WHERE userid = :uid AND tipo = 'DUPLA'")
    long percursosEmDupla(String uid);

    @Getter
    @Setter
    class Historico {
        private float distanciaTotal;
        private long tempoMovimento;
    }

}
