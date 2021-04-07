package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.MenuCorrerFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.historico.HistoricoFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.progresso.ProgressoFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.FragmentUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


public class MainActivity extends AppCompatActivity implements GenericActivity {

    @Setter(AccessLevel.PRIVATE)
    private ActionBar toolbar;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    BottomNavigationView bnvMenu;

    FragmentManager fragmentManager;

    private final Map<Integer, Fragment> mapMenus = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapMenus.put(R.id.menu_item_historico, new HistoricoFragment());
        mapMenus.put(R.id.menu_item_progressso, new ProgressoFragment());
        mapMenus.put(R.id.menu_item_correr, new MenuCorrerFragment());
        mapMenus.put(R.id.menu_item_perfil, new Fragment());

        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFragment(mapMenus.get(R.id.menu_item_correr));
    }

    @Override
    public void initViews() {

        setToolbar(getSupportActionBar());
        setBnvMenu(findViewById(R.id.bnvMenu));
        getBnvMenu().setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        getBnvMenu().setSelectedItemId(R.id.menu_item_correr);
    }

    private void loadFragment(Fragment fragment) {
        new FragmentUtils().loadFragment(this, R.id.fvContainerMain, fragment);
    }

    private boolean onNavigationItemSelected(MenuItem item) {

        String atualId = String.valueOf(item.getItemId());
        String anteriorId = String.valueOf(getBnvMenu().getSelectedItemId());

        if (!atualId.equals(anteriorId)) {
            Fragment atual = mapMenus.get(item.getItemId());

            if (fragmentManager.findFragmentByTag(atualId) != null) { // set o fragment existe, mostra
                fragmentManager.beginTransaction()
                        .show(atual)
                        .commit();

            } else { // senão, adiciona ao manager
                fragmentManager.beginTransaction()
                        .add(R.id.fvContainerMain, atual, atualId)
                        .commit();
            }

            if (fragmentManager.findFragmentByTag(anteriorId) != null) { // se outro fragment está visível, oculta-o
                fragmentManager.beginTransaction()
                        .hide(fragmentManager.findFragmentByTag(anteriorId))
                        .commit();
            }
            return true;
        }
        return false;
    }
}
