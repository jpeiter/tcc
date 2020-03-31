package br.edu.utfpr.pb.jeanpeiter.tcc.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class DialogUtils {

    public AlertDialog.Builder build(Activity activity, int viewId, String title) {
        LayoutInflater inflater = activity.getLayoutInflater();
        return new AlertDialog.Builder(activity)
                .setView(inflater.inflate(viewId, null))
                .setTitle(new ResourcesUtils(activity.getBaseContext()).negrito(title));
    }


}
