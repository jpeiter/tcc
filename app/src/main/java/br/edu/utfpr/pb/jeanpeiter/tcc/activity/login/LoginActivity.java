package br.edu.utfpr.pb.jeanpeiter.tcc.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.bemvindo.BemVindoActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.utils.IntentUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.FirebaseUserController;

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

        FirebaseUser usuarioLogado = FirebaseUserController.getUser();

        if (usuarioLogado == null) {
            showSignInOptions();
        } else {
            new IntentUtils().startActivity(this, BemVindoActivity.class);
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
                new IntentUtils().startActivity(this, BemVindoActivity.class);
            } else {
                Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}


