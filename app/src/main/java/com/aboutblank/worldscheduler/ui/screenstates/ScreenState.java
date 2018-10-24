package com.aboutblank.worldscheduler.ui.screenstates;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ScreenState {
    private final @State int state;

    public ScreenState(@State int state) {
        this.state = state;
    }

    public @State int getState() {
        return state;
    }

    @IntDef({LOADING, DONE, ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public static final int LOADING = 0;
    public static final int DONE = 1;
    public static final int ERROR = 2;
}
