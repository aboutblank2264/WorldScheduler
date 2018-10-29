package com.aboutblank.worldscheduler.ui.screenstates;

public abstract class ScreenState {
    private final State state;

    ScreenState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
