package br.edu.utfpr.pb.jeanpeiter.tcc.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;

public class DialogUtils {

    public AlertDialog.Builder build(Activity activity, int viewId, String title) {
        LayoutInflater inflater = activity.getLayoutInflater();
        return new AlertDialog.Builder(activity)
                .setView(inflater.inflate(viewId, null))
                .setTitle(new ResourcesUtils(activity.getBaseContext()).negrito(title));
    }

    public AlertDialog.Builder build(Context context, String title, String message) {
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setTitle(new ResourcesUtils(context).negrito(title));
    }

    public AlertDialog.Builder build(Context context, String title) {
        return new AlertDialog.Builder(context)
                .setTitle(new ResourcesUtils(context).negrito(title));
    }

    public void setButtonColor(Button button) {
        button.setBackgroundColor(button.getContext().getColor(R.color.branco));
        button.setTextColor(button.getContext().getColor(R.color.primaria));
    }


}
