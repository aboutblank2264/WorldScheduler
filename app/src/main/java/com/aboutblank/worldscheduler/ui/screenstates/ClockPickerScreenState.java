package com.aboutblank.worldscheduler.ui.screenstates;

import java.util.Set;

public class ClockPickerScreenState extends ScreenState {
    private Set<String> timeZones;
    private Throwable throwable;

    public ClockPickerScreenState(State state) {
        super(state);
    }

    public ClockPickerScreenState(State state, Set<String> timeZones) {
        super(state);
        this.timeZones = timeZones;
    }

    public ClockPickerScreenState(State state, Throwable throwable) {
        super(state);
        this.throwable = throwable;
    }

    public Set<String> getTimeZones() {
        return timeZones;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
