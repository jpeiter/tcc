package br.edu.utfpr.pb.jeanpeiter.tcc.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils.ResourcesUtils;


public class BemVindoActivity extends AppCompatActivity implements GenericActivity {

    public TextView tvBemVindo;
    public TextView tvInformeSobre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);
        initViews();
        replace();
    }

    @Override
    public void initViews() {
        this.tvBemVindo = findViewById(R.id.tv_seja_bem_vindo);
        this.tvInformeSobre = findViewById(R.id.tv_informe_sobre);
    }

    @Override
    public void replace() {
        ResourcesUtils resourcesUtils = new ResourcesUtils(getBaseContext());
        tvBemVindo.setText(resourcesUtils.replace(R.string.bem_vindo_x, "Jean"));
        tvInformeSobre.setText(resourcesUtils.pontoFinal(getString(R.string.informe_sobre_voce)));
    }
}
