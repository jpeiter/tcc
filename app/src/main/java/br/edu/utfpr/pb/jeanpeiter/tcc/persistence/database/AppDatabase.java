package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.AtividadeDao;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.AtividadePosicaoDao;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadePosicaoDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;

@Database(
        entities = {
                AtividadeDTO.class,
                AtividadePosicaoDTO.class,
        }, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public final static String DB_NAME = "tcc_ads_jeanpeiter";

    private static AppDatabase instance;

    public abstract AtividadeDao atividadeDao();

    public abstract AtividadePosicaoDao atividadePosicaoDao();


    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public List<Atividade> findByInicioBetween(long inicio, long termino) {
        AtividadeDTO[] dtos = atividadeDao().findByInicioBetween(inicio, termino);
        return dtos != null ? Arrays.asList(dtos).stream().map(dto -> new Atividade().parse(dto)).collect(Collectors.toList()) : new ArrayList<>();
    }

    public Long save(Atividade atividade) throws Exception {
        long atividadeId = atividade.get_id();
        atividadeDao().save(atividade.toDto());
        for (int i = 0; i < atividade.getPosicoes().size(); i++) {
            atividadePosicaoDao().save(atividade.getPosicoes().get(i).toDto(atividadeId));
        }
        return atividadeId;
    }

    public void deleteAtividade(Atividade atividade) {
        atividadeDao().delete(atividade.get_id());
        atividadePosicaoDao().delete(atividade.get_id());
    }

    public List<AtividadePosicao> findPosicoesByAtividade(Long atividadeId) {
        AtividadePosicaoDTO[] dtos = atividadePosicaoDao().findByAtividade(atividadeId);
        return dtos != null ? Arrays.asList(dtos).stream().map(dto -> new AtividadePosicao().parse(dto)).collect(Collectors.toList()) : new ArrayList<>();
    }


}
