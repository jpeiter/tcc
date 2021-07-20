package br.edu.utfpr.pb.jeanpeiter.tcc.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;

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
        return custom(texto, inicio, fim, R.font.roboto_bold);
    }

    public BitmapDescriptor bitmapDescriptorFromVector(int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
