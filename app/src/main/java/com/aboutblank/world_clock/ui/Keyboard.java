package com.aboutblank.world_clock.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class Keyboard {

    private static InputMethodManager getImm(Fragment fragment) {
        return (InputMethodManager) fragment.requireContext()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
    }

    public static void showKeyboard(Fragment fragment) {
        getImm(fragment).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyboard(Fragment fragment) {
        getImm(fragment).hideSoftInputFromWindow(fragment.getView().getWindowToken(), 0);
    }

    public static void hideKeyboard(Activity activity) {
        ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(new View(activity).getWindowToken(), 0);
    }
}
