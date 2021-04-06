package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.nivelprogresso;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.HistoricoAtividadesDto;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.UsuarioNivelProgresso;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.dto.UsuarioNivelProgressoDTO;

@Dao
public interface UsusarioNivelProgressoDao {

    @Update
    void update(UsuarioNivelProgressoDTO nivelProgresso);

    @Insert
    void create(List<UsuarioNivelProgressoDTO> nivelProgresso);

    @Query(UsusarioNivelProgressoQueries.NIVEL_ATUAL)
    UsuarioNivelProgressoDTO nivelAtual(String uid);

    @Query(UsusarioNivelProgressoQueries.HISTORICO)
    List<UsuarioNivelProgressoDTO> historico(String uid);

}
