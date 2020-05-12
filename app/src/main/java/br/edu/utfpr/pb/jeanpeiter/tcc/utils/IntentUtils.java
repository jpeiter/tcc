package br.edu.utfpr.pb.jeanpeiter.tcc.utils;

import android.content.Context;
import android.content.Intent;

public class IntentUtils {

    public void startActivity(Context context, Class activity) {
        startActivity(context, new Intent(context, activity));
    }

    public void startActivity(Context context, Intent i){
        context.startActivity(i);
    }


}
