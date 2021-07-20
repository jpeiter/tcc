package br.edu.utfpr.pb.jeanpeiter.tcc.controller.nivelprogresso;

import android.content.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.helper.NivelProgressoHelper;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.nivelprogresso.UsuarioNivelProgressoDatabase;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.NivelProgresso;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.UsuarioNivelProgresso;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NivelProgressoController {

    private final Context context;

    public void save(String uid, Long pontuacao) {
        UsuarioNivelProgressoDatabase db = new UsuarioNivelProgressoDatabase(context);

        UsuarioNivelProgresso nivelAtual = db.nivelAtual(uid);
        List<UsuarioNivelProgresso> nivelAtualizado = new NivelProgressoHelper().nivelAtualizado(nivelAtual, uid, pontuacao);
        db.save(nivelAtualizado);
    }


    public List<UsuarioNivelProgresso> historico(String uid) {
        List<UsuarioNivelProgresso> db = new UsuarioNivelProgressoDatabase(context).historico(uid);
        return db != null ? db : new ArrayList<>();
    }
}
