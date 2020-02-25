package br.edu.utfpr.pb.jeanpeiter.tcc.activity;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

abstract class AbstractActivity extends AppCompatActivity {

    public void initViews(List<View> views, List<Integer> ids) {
        for (int i = 0; i < views.size(); i++) {
            View v = views.get(i);
            v = findViewById(ids.get(i));
        }
    }


}
