package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;

@Database(
        entities = {
                Atividade.class,
                AtividadePosicao.class,
        }, version = 1)
public abstract class AppDatabase extends RoomDatabase {


}
