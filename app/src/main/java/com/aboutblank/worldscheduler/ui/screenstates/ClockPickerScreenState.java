package com.aboutblank.worldscheduler.ui.screenstates;

import com.aboutblank.worldscheduler.backend.room.TimeZone;

import java.util.List;

public class ClockPickerScreenState extends ScreenState {
    private List<TimeZone> timeZones;
    private Throwable throwable;
    private String message;

    public ClockPickerScreenState(State state) {
        super(state);
    }

    public ClockPickerScreenState(State state, List<TimeZone> timeZones) {
        super(state);
        this.timeZones = timeZones;
    }

    public ClockPickerScreenState(State state, Throwable throwable) {
        super(state);
        this.throwable = throwable;
    }

    public ClockPickerScreenState(final State state, final String message) {
        super(state);
        this.message = message;
    }

    public List<TimeZone> getTimeZones() {
        return timeZones;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getMessage() {
        return message;
    }
}
