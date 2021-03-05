package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.AtividadeDao;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.dao.AtividadePosicaoDao;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadeDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.AtividadePosicaoDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.HistoricoAtividades;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.historico.HistoricoAtividadesDto;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.resumo.AtividadeResumo;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.DateUtils;

@Database(
        entities = {
                AtividadeDTO.class,
                AtividadePosicaoDTO.class,
        }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private final static String DB_NAME = "tcc_ads_jeanpeiter";

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

    public List<AtividadeResumo> findByInicioBetween(LocalDate inicio, LocalDate termino) {
        DateUtils dateUtils = new DateUtils();
        List<Atividade> resumos = atividadeDao().findByInicioBetween(
                dateUtils.localDateToMillis(inicio, LocalTime.MIN),
                dateUtils.localDateToMillis(termino, LocalTime.MAX)
        );
        return resumos != null ? resumos.stream().map(AtividadeResumo::new).collect(Collectors.toList()) : new ArrayList<>();
    }

    public String save(Atividade atividade) throws Exception {
        String atividadeId = atividade.get_id();
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

    public List<AtividadePosicao> findPosicoesByAtividade(String atividadeId) {
        AtividadePosicaoDTO[] dtos = atividadePosicaoDao().findByAtividade(atividadeId);
        return dtos != null ? Arrays.stream(dtos).map(dto -> new AtividadePosicao().parse(dto)).collect(Collectors.toList()) : new ArrayList<>();
    }

    public HistoricoAtividades getHistorico(String uid) {
        return Optional.ofNullable(atividadeDao().historico(uid))
                .map(HistoricoAtividadesDto::parse)
                .get();
    }

    public long percursosEmDupla(String uid) {
        return atividadeDao().percursosEmDupla(uid);
    }


}
