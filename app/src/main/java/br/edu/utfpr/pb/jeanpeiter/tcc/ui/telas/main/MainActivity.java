package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.MenuCorrerFragment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainActivity extends AppCompatActivity implements GenericActivity {

    private ActionBar toolbar;

    BottomNavigationView bnvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
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


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fvContainerMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
