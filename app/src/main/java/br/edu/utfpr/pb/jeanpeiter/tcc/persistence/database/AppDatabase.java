package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;

@Database(
        entities = {
                AtividadeDTO.class,
//                AtividadePosicaoDTO.class,
        }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
                    .build();
        }
        return instance;
    }

    public final static String DB_NAME = "tcc_ads_jeanpeiter";

    public abstract AtividadeDao atividadeDao();

    public Atividade findByInicioBetween(long inicio, long termino) {
        AtividadeDTO dto = atividadeDao().findByInicioBetween(inicio, termino);
        return dto != null ? new Atividade().parse(dto) : null;
    }

    public void save(Atividade atividade) {
        atividadeDao().save(atividade.toDto());
    }

    public void deleteAtividade(Atividade atividade) {
        atividadeDao().delete(atividade.toDto());
    }


}
