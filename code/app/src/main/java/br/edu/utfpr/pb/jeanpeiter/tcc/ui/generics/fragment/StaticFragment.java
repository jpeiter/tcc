package br.edu.utfpr.pb.jeanpeiter.tcc.ui.generics.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;

public class StaticFragment extends Fragment {

    @LayoutRes
    private int resId;

    public StaticFragment(@LayoutRes int id) {
        this.resId = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(resId, container, false);
    }
}
