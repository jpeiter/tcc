package br.edu.utfpr.pb.jeanpeiter.tcc.ui.telas.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.firebase.ui.auth.IdpResponse;
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
        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void initViews() {
        mapMenus.put(R.id.menu_item_historico, new HistoricoFragment());
        mapMenus.put(R.id.menu_item_progressso, new ProgressoFragment());
        mapMenus.put(R.id.menu_item_correr, new MenuCorrerFragment());
//        mapMenus.put(R.id.menu_item_perfil, new Fragment());

        setToolbar(getSupportActionBar());
        setBnvMenu(findViewById(R.id.bnvMenu));
        getBnvMenu().setOnNavigationItemSelectedListener(item -> onNavigationItemSelected(item.getItemId()));
        getBnvMenu().setSelectedItemId(R.id.menu_item_correr);
    }

    private boolean onNavigationItemSelected(int itemId) {

        String atualId = String.valueOf(itemId);
        String anteriorId = String.valueOf(getBnvMenu().getSelectedItemId());

        Fragment atual = mapMenus.get(itemId);
        if (!atualId.equals(anteriorId)) {

            if (fragmentManager.findFragmentByTag(atualId) != null) { // set o fragment existe, mostra
                fragmentManager.beginTransaction()
                        .show(atual)
                        .commit();

            } else { // senão, adiciona ao manager
                fragmentManager.beginTransaction()
                        .add(R.id.fvContainerMain, atual, atualId)
                        .commit();
            }
            Fragment anteriorVisivel = fragmentManager.findFragmentByTag(anteriorId);
            if (anteriorVisivel != null) { // se outro fragment está visível, oculta-o
                fragmentManager.beginTransaction()
                        .hide(anteriorVisivel)
                        .commit();
            }
            return true;
        }
        return false;
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (false) {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
////        getBnvMenu().performClick();
////        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
////            fragment.onActivityResult(requestCode, resultCode, data);
////        }
//        int menu_id = data.getIntExtra("menu_id", R.id.menu_item_correr);
//        onNavigationItemSelected(menu_id, true);
//    }
}
