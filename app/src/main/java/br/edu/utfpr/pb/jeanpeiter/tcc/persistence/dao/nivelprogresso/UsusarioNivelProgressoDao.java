package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.nivelprogresso;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.dto.UsuarioNivelProgressoDTO;

@Dao
public interface UsusarioNivelProgressoDao {

    @Insert
    void create(List<UsuarioNivelProgressoDTO> niveisProgresso);

    @Update
    void update(UsuarioNivelProgressoDTO nivelProgresso);

    @Query(UsusarioNivelProgressoQueries.NIVEL_ATUAL)
    UsuarioNivelProgressoDTO nivelAtual(String uid);

    @Query(UsusarioNivelProgressoQueries.HISTORICO)
    List<UsuarioNivelProgressoDTO> historico(String uid);

}
