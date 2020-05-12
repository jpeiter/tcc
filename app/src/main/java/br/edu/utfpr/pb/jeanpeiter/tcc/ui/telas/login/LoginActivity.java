package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseUserController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.dto.UsuarioDTO;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences.AppSharedPreferences;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.bemvindo.BemVindoActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.MainActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.IntentUtils;

import static br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseUserController.getUser;

public class LoginActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 200;

    private final List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.FacebookBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        FirebaseUser usuarioLogado = getUser();

        if (usuarioLogado == null) {
            showSignInOptions();
        } else {
            verificaUsuarioExistente();
        }
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAlwaysShowSignInMethodScreen(true)
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme)
                        .build(),
                REQUEST_CODE
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {

            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                verificaUsuarioExistente();
            } else {
                Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void verificaUsuarioExistente() {
        FirebaseUserController.fetchInfo().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Usuario usuario = dataSnapshot.getValue(UsuarioDTO.class).parse(dataSnapshot.getKey());
                    new AppSharedPreferences(LoginActivity.this).putUsuario(usuario);
                    new IntentUtils().startActivity(LoginActivity.this, MainActivity.class);
                } else {
                    new IntentUtils().startActivity(LoginActivity.this, BemVindoActivity.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }
}


