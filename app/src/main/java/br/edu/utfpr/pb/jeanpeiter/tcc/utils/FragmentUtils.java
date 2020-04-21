package br.edu.utfpr.pb.jeanpeiter.tcc.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils {

    public void loadFragment(FragmentActivity currentActivity, int frameId, Fragment newFragment) {
        FragmentTransaction transaction = currentActivity.getSupportFragmentManager().beginTransaction();
        this.load(transaction, frameId, newFragment);
    }

    public void loadFragment(Fragment currentFragment, int frameId, Fragment newFragment) {
        FragmentTransaction transaction = currentFragment.getFragmentManager().beginTransaction();
        this.load(transaction, frameId, newFragment);
    }

    private void load(FragmentTransaction transaction, int frameId, Fragment newFragment) {
        transaction.replace(frameId, newFragment);
        transaction.commit();
    }

    public void kill(Fragment fragment){
        FragmentManager manager = fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(fragment);
        trans.commit();
        manager.popBackStack();
    }



}
