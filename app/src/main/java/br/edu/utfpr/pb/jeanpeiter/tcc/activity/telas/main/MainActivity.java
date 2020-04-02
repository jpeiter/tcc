package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.main;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.generics.PermissionActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.main.fragments.MenuCorrerFragment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainActivity extends AppCompatActivity implements GenericActivity, PermissionActivity {

    private ActionBar toolbar;

    BottomNavigationView bnvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        grantPermissions();
        loadFragment(new MenuCorrerFragment());

    }

    @Override
    public void initViews() {
        setToolbar(getSupportActionBar());
        setBnvMenu(findViewById(R.id.bnvMenu));
        getBnvMenu().setSelectedItemId(R.id.menu_item_correr);
        getBnvMenu().setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_item_historico:
                    Toast.makeText(MainActivity.this, getString(R.string.historico), Toast.LENGTH_SHORT);
                    loadFragment(new Fragment());
                    return true;

                case R.id.menu_item_correr:
                    Toast.makeText(MainActivity.this, getString(R.string.correr), Toast.LENGTH_SHORT);
                    loadFragment(new MenuCorrerFragment());
                    return true;

                case R.id.menu_item_perfil:
                    Toast.makeText(MainActivity.this, getString(R.string.perfil), Toast.LENGTH_SHORT);
                    loadFragment(new Fragment());
                    return true;

                default:
                    return false;
            }
        });
    }

    @Override
    public void grantPermissions() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        Toast.makeText(MainActivity.this, "AAAAAAAAAA", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fvContainerMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
