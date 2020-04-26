package br.edu.utfpr.pb.jeanpeiter.tcc.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class AnimationUtils {

    public Animation piscar(){
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(700);
        anim.setStartOffset(200);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        return anim;
    }
}
