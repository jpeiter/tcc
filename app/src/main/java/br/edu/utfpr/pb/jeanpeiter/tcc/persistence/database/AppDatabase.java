package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.atividade.AtividadeDao;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.nivelprogresso.UsusarioNivelProgressoDao;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.migration.MigrationManager;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadePosicaoDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.dto.UsuarioNivelProgressoDTO;

@Database(
        exportSchema = false,
        entities = {
                AtividadeDTO.class,
                AtividadePosicaoDTO.class,
                UsuarioNivelProgressoDTO.class,
        }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private final static String DB_NAME = "tcc_ads_jeanpeiter";

    private static AppDatabase instance;

    public abstract AtividadeDao atividadeDao();
    public abstract UsusarioNivelProgressoDao ususarioNivelProgressoDao();


    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
//                    .addMigrations(MigrationManager.getAll())
                    .build();
        }
        return instance;
    }

}
