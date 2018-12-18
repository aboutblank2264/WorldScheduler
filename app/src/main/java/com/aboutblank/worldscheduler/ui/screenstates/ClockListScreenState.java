package com.aboutblank.worldscheduler.ui.screenstates;

import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.backend.room.Clock;

import java.util.List;

public class ClockListScreenState {
    private ClockListState state;
    private List<Clock> clocks;
    private List<Long> savedTimes;
    private Throwable throwable;
    private long millisOfDay;
    private String timeZoneId;
    private String offsetString;
    private String[] formattedTimeStrings;

    @SuppressWarnings("unused")
    private ClockListScreenState() {
    }

    private ClockListScreenState(ClockListState state) {
        this.state = state;
    }

    @SuppressWarnings("unchecked")
    private ClockListScreenState(final ClockListState state, @NonNull Object... objects) {
        this.state = state;
        switch (state) {
            case LOADING:
                break;
            case CLOCKS:
                clocks = (List<Clock>) objects[0];
                break;
            case ERROR:
                throwable = (Throwable) objects[0];
                break;
            case LOCAL_TIMEZONE:
                timeZoneId = (String) objects[0];
                break;
            case MILLIS_OF_DAY:
                millisOfDay = (long) objects[0];
                break;
            case OFFSET_STRING:
                offsetString = (String) objects[0];
                break;
            case FORMAT_TIME_STRINGS:
                formattedTimeStrings = (String[]) objects[0];
                break;
            case DELETE_CLOCK:
                timeZoneId = (String) objects[0];
                break;
            case ADD_NEW_SAVED_TIME:
            case DELETE_SAVED_TIME:
            case GET_SAVED_TIMES:
                timeZoneId = (String) objects[0];
                savedTimes = (List<Long>) objects[1];
                break;
        }
    }

    public static ClockListScreenState loading() {
        return new ClockListScreenState(ClockListState.LOADING);
    }

    public static ClockListScreenState clocks(List<Clock> clocks) {
        return new ClockListScreenState(ClockListState.CLOCKS, clocks);
    }

    public static ClockListScreenState error(Throwable throwable) {
        return new ClockListScreenState(ClockListState.ERROR, throwable);
    }

    public static ClockListScreenState localTimeZone(String timeZoneId) {
        return new ClockListScreenState(ClockListState.LOCAL_TIMEZONE, timeZoneId);
    }

    public static ClockListScreenState millisOfDay(long millisOfDay) {
        return new ClockListScreenState(ClockListState.MILLIS_OF_DAY, millisOfDay);
    }

    public static ClockListScreenState offsetString(String offsetString) {
        return new ClockListScreenState(ClockListState.OFFSET_STRING, offsetString);
    }

//    public static ClockListScreenState formatTimeStrings(String[] formatTimeStrings) {
//        return new ClockListScreenState(ClockListState.FORMAT_TIME_STRINGS, formatTimeStrings);
//    }

    public static ClockListScreenState deleteClock(String timeZoneId) {
        return new ClockListScreenState(ClockListState.DELETE_CLOCK, timeZoneId);
    }

    public static ClockListScreenState deleteSavedTime(final String timeZoneId, final List<Long> savedTimes) {
        return new ClockListScreenState(ClockListState.DELETE_SAVED_TIME, timeZoneId, savedTimes);
    }

    public static ClockListScreenState addNewSavedTime(final String timeZoneId, final List<Long> savedTimes) {
        return new ClockListScreenState(ClockListState.ADD_NEW_SAVED_TIME, timeZoneId, savedTimes);
    }

    public static ClockListScreenState getSavedTimes(final String timeZoneId, final List<Long> savedTimes) {
        return new ClockListScreenState(ClockListState.GET_SAVED_TIMES, timeZoneId, savedTimes);
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

    public List<Long> getSavedTimes() {
        return savedTimes;
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
        GET_SAVED_TIMES,
        ADD_NEW_SAVED_TIME,
        DELETE_CLOCK,
        DELETE_SAVED_TIME
    }
}
