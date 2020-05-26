package br.edu.utfpr.pb.jeanpeiter.tcc.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class IntentUtils {

    public void startActivityForResult(Activity origem, Class destino, int reqCode) {
        origem.startActivityForResult(new Intent(origem, destino), reqCode);
    }

    public void startActivity(Context context, Class activity) {
        startActivity(context, new Intent(context, activity));
    }

    public void startActivity(Context context, Intent i) {
        context.startActivity(i);
    }


}
