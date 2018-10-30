package com.aboutblank.worldscheduler.ui.screenstates;

import com.aboutblank.worldscheduler.backend.room.Clock;

import java.util.List;

public class ClockListScreenState extends ScreenState {
    private List<Clock> clocks;
    private Throwable throwable;

    public ClockListScreenState(State state) {
        super(state);
    }

    public ClockListScreenState(State state, List<Clock> clocks) {
        super(state);
        this.clocks = clocks;
    }

    public ClockListScreenState(State state, Throwable throwable) {
        super(state);
        this.throwable = throwable;
    }

    public List<Clock> getClocks() {
        return clocks;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public String toString() {
        return "ClockListScreenState { " +
                "clocks = " + clocks +
                ", throwable = " + throwable +
                '}';
    }
}
