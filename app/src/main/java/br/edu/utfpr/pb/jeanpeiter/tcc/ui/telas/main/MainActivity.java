package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.MapsInitializer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.GenericActivity;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.MenuCorrerFragment;
import br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main.fragments.historico.HistoricoFragment;
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

    private Map<Integer, Fragment> mapMenus = new HashMap<Integer, Fragment>() {{
        put(R.id.menu_item_historico, new HistoricoFragment());
        put(R.id.menu_item_correr, new MenuCorrerFragment());
        put(R.id.menu_item_perfil, new Fragment());
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        getBnvMenu().setSelectedItemId(R.id.menu_item_correr);
        getBnvMenu().setOnNavigationItemSelectedListener((item) -> {
            if (item.getItemId() != getBnvMenu().getSelectedItemId()) {
                loadFragment(mapMenus.get(item.getItemId()));
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        new FragmentUtils().loadFragment(this, R.id.fvContainerMain, fragment);
    }

}
