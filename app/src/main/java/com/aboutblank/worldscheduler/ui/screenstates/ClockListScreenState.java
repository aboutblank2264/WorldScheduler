package com.aboutblank.worldscheduler.ui.screenstates;

import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.backend.room.Clock;

import java.util.List;

public class ClockListScreenState {
    private ClockListState state;
    private List<Clock> clocks;
    private Throwable throwable;
    private long millisOfDay;
    private String timeZoneId;
    private String offsetString;
    private String[] formattedTimeStrings;

    @SuppressWarnings("unused")
    private ClockListScreenState() {}

    public ClockListScreenState(ClockListState state) {
        this.state = state;
    }

    @SuppressWarnings("unchecked")
    public ClockListScreenState(final ClockListState state, @NonNull Object object) {
        this.state = state;
        switch (state) {
            case LOADING:
                break;
            case CLOCKS:
                clocks = (List<Clock>) object;
                break;
            case ERROR:
                throwable = (Throwable) object;
                break;
            case LOCAL_TIMEZONE:
                timeZoneId = (String) object;
                break;
            case MILLIS_OF_DAY:
                millisOfDay = (long) object;
                break;
            case OFFSET_STRING:
                offsetString = (String) object;
                break;
            case FORMAT_TIME_STRINGS:
                formattedTimeStrings = (String[]) object;
                break;
            case DELETE_CLOCK:
                break;
            case DELETE_TIME:
                break;
        }
    }

    public List<Clock> getClocks() {
        return clocks;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public ClockListState getState() {
        return state;
    }

    public long getMillisOfDay() {
        return millisOfDay;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public String getOffsetString() {
        return offsetString;
    }

    public String[] getFormattedTimeStrings() {
        return formattedTimeStrings;
    }

    @Override
    public String toString() {

        return "ClockListScreenState : " + state;
//        StringBuilder stringBuilder = new StringBuilder("ClockListScreenState { ");
//        stringBuilder.append(state.toString());
//        stringBuilder.append(" ");
//
//        if(clocks != null) {
//            stringBuilder.append(clocks.toString());
//        }
//        stringBuilder.append("}");
//
//        return stringBuilder.toString();
    }

    public enum ClockListState {
        LOADING,
        CLOCKS,
        ERROR,
        LOCAL_TIMEZONE,
        MILLIS_OF_DAY,
        OFFSET_STRING,
        FORMAT_TIME_STRINGS,
        ADD_NEW_SAVED_TIME,
        DELETE_CLOCK,
        DELETE_TIME
    }
}
