package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.atividade;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeActivity extends AppCompatActivity {

    protected AtividadeEstado atividadeEstado = AtividadeEstado.PAUSADA;

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
        setAtividadeEstado(AtividadeEstado.EM_ANDAMENTO);
    }

    @Override
    public void onBackPressed() {
        AtividadeFragment fragment = (AtividadeFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container_atividade);
        fragment.getBtnPausarParar().performClick();
        switch (getAtividadeEstado()) {
            case PAUSADA:
                super.onBackPressed();
                break;
        }
    }


    public void retomarAtividade() {
        setAtividadeEstado(AtividadeEstado.EM_ANDAMENTO);
    }

    public void pausarAtividade() {
        setAtividadeEstado(AtividadeEstado.PAUSADA);
    }

    public void finalizarAtividade() {
        finish();
    }
}
