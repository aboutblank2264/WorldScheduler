package com.aboutblank.worldscheduler.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.aboutblank.worldscheduler.R;

public class MainFragmentManager {
    private FragmentManager manager;

    public MainFragmentManager(@NonNull MainActivity mainActivity) {
        this.manager = mainActivity.getSupportFragmentManager();
    }

    public void changeFragmentView(Fragment fragment) {
        changeFragmentView(fragment, false);
    }

    public void changeFragmentView(Fragment fragment, boolean backStack) {
        FragmentTransaction transaction = manager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.main_placeholder, fragment);

        if (backStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }

    public void finishCurrentFragment() {
        manager.popBackStack();
    }
}
