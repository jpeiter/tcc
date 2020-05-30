package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.dupla;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.connectivity.info.NetworkInformation;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseUserStatusController;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.atividade.dupla.fragments.selecionarparceiro.SelecionarParceiroFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;

public class SelecionarParceiroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_parceiro);

        if (NetworkInformation.isNetworkAvailable(this)) {
            FirebaseUserStatusController.getDatabase().getDatabase().goOnline();
            new FragmentUtils().loadFragment(this, R.id.fl_selecionar_parceiro, new SelecionarParceiroFragment());
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (NetworkInformation.isNetworkAvailable(this)) {
            FirebaseUserStatusController.getDatabase().getDatabase().goOffline();
        }
    }
}
