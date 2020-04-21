package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.atividade;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;

public class AtividadeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);

        new FragmentUtils().loadFragment(this, R.id.fl_container_atividade, new ContagemRegressivaFragment());
    }

    void iniciarAtividade() {
        FragmentUtils fragmentUtils = new FragmentUtils();
        ContagemRegressivaFragment fragment = (ContagemRegressivaFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_atividade);

        fragmentUtils.kill(fragment);
        fragmentUtils.loadFragment(this, R.id.fl_container_atividade, new AtividadeFragment());
    }
    
}
