package br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;
import lombok.AllArgsConstructor;

import static android.content.Context.MODE_PRIVATE;

@AllArgsConstructor
public class AppSharedPreferences {

    private final String APP_NAME = "TCC_ADS_JEAN_FELIPE_PEITER";
    private final String KEY_CONFIGURACOES = "configuracoes";
    private final String KEY_USUARIO = "usuario";

    private Context context;

    private SharedPreferences getPreferences() {
        return context.getSharedPreferences(APP_NAME, MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    public void putUsuario(Usuario usuario) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(KEY_USUARIO, new Gson().toJson(usuario));
        editor.commit();
    }

    public Usuario getUsuario() {
        SharedPreferences sp = getPreferences();
        String json = sp.getString(KEY_USUARIO, null);
        return new Gson().fromJson(json, Usuario.class);
    }

    public void removeUsuario() {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(KEY_USUARIO);
        editor.commit();
    }

    public void putConfiguracoes(AppConfiguracoes configuracoes) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(KEY_USUARIO, new Gson().toJson(configuracoes));
        editor.commit();
    }

    public AppConfiguracoes getConfiguracoes() {
        SharedPreferences sp = getPreferences();
        String json = sp.getString(KEY_CONFIGURACOES, null);
        return json != null ? new Gson().fromJson(json, AppConfiguracoes.class) : new AppConfiguracoes();
    }
}
