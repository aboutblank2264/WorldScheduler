package com.aboutblank.worldscheduler.ui.screenstates;

import com.aboutblank.worldscheduler.backend.time.TimeZone;

import java.util.List;

public class ClockPickerScreenState {
    private ClockPickerState state;
    private List<TimeZone> timeZones;
    private Throwable throwable;
    private String message;

    public ClockPickerScreenState(ClockPickerState state, List<TimeZone> timeZones) {
        this.state = state;
        this.timeZones = timeZones;
    }

    public ClockPickerScreenState(ClockPickerState state, Throwable throwable) {
        this.state = state;
        this.throwable = throwable;
    }

    public ClockPickerScreenState(final ClockPickerState state, final String message) {
        this.state = state;
        this.message = message;
    }

    public ClockPickerState getState() {
        return state;
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

    public enum ClockPickerState {
        DONE,
        ERROR,
        MESSAGE
    }
}
