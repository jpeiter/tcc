package br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils;

import android.content.Context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ResourcesUtils {

    private String str;
    private Context context;

    public ResourcesUtils(Context context){
        setContext(context);
    }

    public ResourcesUtils replaceString(int id, String... args) {
        String str = getContext().getString(id);
        for (int i = 0; i < args.length; i++) {
            str = str.replace(":arg" + i, args[i]);
        }
        setStr(str);
        return this;
    }

    public String replace(int id, String... args){
        return replaceString(id, args).getStr();
    }

    public String pontoFinal(String str){
        return str.concat(".");
    }

}
