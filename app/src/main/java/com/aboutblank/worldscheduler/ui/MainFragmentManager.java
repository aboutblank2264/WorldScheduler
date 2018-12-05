package com.aboutblank.worldscheduler.ui;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.ui.screens.ClockPickerFragment;

public class MainFragmentManager {
    private FragmentManager manager;
    private Context mainContext;

    public MainFragmentManager(@NonNull MainActivity mainActivity) {
        this.manager = mainActivity.getSupportFragmentManager();
        this.mainContext = mainActivity;
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

    public void changeToPickerFragment(boolean backStack) {
        changeFragmentView(new ClockPickerFragment(), backStack);
    }

    public void finishCurrentFragment() {
        manager.popBackStack();
    }

    public void showDialog(DialogFragment dialogFragment, String tag) {
        dialogFragment.show(manager, tag);
    }

    public void addAlarm(final String tag, final int hour, final int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, tag);

        mainContext.startActivity(intent);
    }
}
