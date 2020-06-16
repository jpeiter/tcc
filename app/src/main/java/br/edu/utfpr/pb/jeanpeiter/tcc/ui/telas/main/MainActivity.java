package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.MenuCorrerFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;
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
        new FragmentUtils().loadFragment(this, R.id.fvContainerMain, new MenuCorrerFragment());
    }

    @Override
    public void initViews() {
        setToolbar(getSupportActionBar());
        setBnvMenu(findViewById(R.id.bnvMenu));
        getBnvMenu().setSelectedItemId(R.id.menu_item_correr);
        getBnvMenu().setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_item_historico:
                    new FragmentUtils().loadFragment(this, R.id.fvContainerMain, new Fragment());
                    return true;

                case R.id.menu_item_correr:
                    new FragmentUtils().loadFragment(this, R.id.fvContainerMain, new MenuCorrerFragment());
                    return true;

                case R.id.menu_item_perfil:
                    new FragmentUtils().loadFragment(this, R.id.fvContainerMain, new Fragment());
                    return true;

                default:
                    return false;
            }
        });
    }

}
