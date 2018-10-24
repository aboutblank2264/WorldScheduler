package com.aboutblank.worldscheduler.ui.screenstates;

import android.support.annotation.IntDef;

import com.aboutblank.worldscheduler.backend.room.Clock;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class ScreenState {
    private final @State int state;
    private final List<Clock> clocks;

    public ScreenState(@State int state, List<Clock> clocks) {
        this.state = state;
        this.clocks = clocks;
    }

    public @State
    int getState() {
        return state;
    }

    public List<Clock> getClocks() {
        return clocks;
    }

    @IntDef({LOADING, DONE, ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public static final int LOADING = 0;
    public static final int DONE = 1;
    public static final int ERROR = 2;
}
