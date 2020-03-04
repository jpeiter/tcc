package br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

import androidx.core.content.res.ResourcesCompat;

import br.edu.utfpr.pb.jeanpeiter.tcc.activity.resources.Fonte;

public class ResourcesUtils {

    private Context context;

    public ResourcesUtils(Context context) {
        this.context = context;
    }

    public String replace(int id, String... args) {
        String str = this.context.getString(id);
        for (int i = 0; i < args.length; i++) {
            str = str.replace(":arg" + i, args[i]);
        }
        return str;
    }

    public String pontoFinal(String str) {
        return str.concat(".");
    }

    private SpannableString custom(String texto, int inicio, int fim, int fonte) {
        Typeface typeface = ResourcesCompat.getFont(context, fonte);
        SpannableString span = new SpannableString(texto);
        span.setSpan(new StyleSpan(typeface.getStyle()), inicio, fim, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return span;
    }

    public SpannableString negrito(String texto) {
        return negrito(texto, 0, texto.length());
    }

    public SpannableString negrito(String texto, int inicio, int fim) {
        return custom(texto, inicio, fim, Fonte.BOLD);
    }

}
