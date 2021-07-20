package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.nivelprogresso;

import android.content.Context;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.AppDatabase;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.UsuarioNivelProgresso;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.dto.UsuarioNivelProgressoDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UsuarioNivelProgressoDatabase {

    private final Context context;

    public void save(List<UsuarioNivelProgresso> niveis) {
        List<UsuarioNivelProgressoDTO> dtos = niveis.stream().map(UsuarioNivelProgresso::toDto).collect(Collectors.toList());

        dtos.stream()
                .filter(d -> d.get_id() != null)
                .findFirst()
                .ifPresent(atual ->
                        AppDatabase.getInstance(context).ususarioNivelProgressoDao().update(atual)
                );

        List<UsuarioNivelProgressoDTO> novos = dtos.stream()
                .filter(d -> d.get_id() == null)
                .peek(d -> d.set_id(UUID.randomUUID().toString()))
                .collect(Collectors.toList());
        if (!novos.isEmpty()) {
            AppDatabase.getInstance(context).ususarioNivelProgressoDao().create(novos);
        }
    }

    public UsuarioNivelProgresso nivelAtual(String uid) {
        UsuarioNivelProgressoDTO dto = AppDatabase.getInstance(context).ususarioNivelProgressoDao().nivelAtual(uid);
        return dto != null ? new UsuarioNivelProgresso().parse(dto) : null;
    }

    public List<UsuarioNivelProgresso> historico(String uid) {
        List<UsuarioNivelProgressoDTO> dtos = AppDatabase.getInstance(context).ususarioNivelProgressoDao().historico(uid);
        return dtos != null ? dtos.stream().map(d -> new UsuarioNivelProgresso().parse(d)).collect(Collectors.toList()) : null;
    }

}
