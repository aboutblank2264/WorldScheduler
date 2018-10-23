package com.aboutblank.worldscheduler.model;

import com.aboutblank.worldscheduler.ui.screenstates.ScreenState;

abstract class BaseModel {
    private final @ScreenState.State int state;
    private final Throwable error;

    BaseModel(int state, Throwable error) {
        this.state = state;
        this.error = error;
    }

    public int getState() {
        return state;
    }

    public Throwable getError() {
        return error;
    }
}
