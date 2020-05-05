package br.edu.utfpr.pb.jeanpeiter.tcc.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;
import lombok.AllArgsConstructor;

import static android.content.Context.MODE_PRIVATE;

@AllArgsConstructor
public class SharedPreferencesUtils {

    private final String APP_NAME = "TCC_ADS_JEAN_FELIPE_PEITER";

    private Context context;

    private SharedPreferences.Editor getEditor() {
        return context.getSharedPreferences(APP_NAME, MODE_PRIVATE).edit();
    }

    public void saveUsuario(Usuario json) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString("usuario", new Gson().toJson(json));
        editor.commit();
    }


    public void removeUsuario() {
        SharedPreferences.Editor editor = getEditor();
        editor.remove("usuario");
        editor.commit();
    }
}
