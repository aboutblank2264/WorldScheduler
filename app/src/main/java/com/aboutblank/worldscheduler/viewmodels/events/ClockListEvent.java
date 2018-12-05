package com.aboutblank.worldscheduler.viewmodels.events;

import android.os.Bundle;
import android.support.annotation.NonNull;

public final class ClockListEvent {
    private final static String TIMEZONEID = "timezoneId";
    private final static String TIMEPOSN = "timePosn";
    private final static String TIMESTR = "timeStr";
    private final static String TAG = "tag";
    private final static String HOUR = "hour";
    private final static String MINUTE = "minute";

    private Event event;
    private Bundle bundle;

    private ClockListEvent(final Event event) {
        this.event = event;
    }

    private ClockListEvent(final Event event, final Object... data) {
        this.event = event;
        this.bundle = new Bundle();
        switch (event) {
            case LOAD_CLOCKS:
                break;
            case DELETE_CLOCK:
                bundle.putString(TIMEZONEID, (String) data[0]);
                break;
            case ADD_SAVED_TIME:
                bundle.putString(TIMEZONEID, (String) data[0]);
                bundle.putInt(HOUR, (int) data[1]);
                bundle.putInt(MINUTE, (int) data[2]);
                break;
            case DELETE_SAVED_TIME:
                bundle.putString(TIMEZONEID, (String) data[0]);
                bundle.putInt(TIMEPOSN, (int) data[1]);
                break;
            case ADD_ALARM:
                bundle.putString(TIMESTR, (String) data[0]);
                bundle.putString(TAG, (String) data[1]);
                break;
            case GET_LOCAL_TIMEZONE:
                break;
            case GET_OFFSET_STRING:
                bundle.putString(TIMEZONEID, (String) data[0]);
                break;
            case FAB_CLICK:
                break;
        }
    }

    public static ClockListEvent loadClock() {
        return new ClockListEvent(Event.LOAD_CLOCKS);
    }

    public static ClockListEvent deleteClock(@NonNull final String timeZoneId) {
        return new ClockListEvent(Event.DELETE_CLOCK, timeZoneId);
    }

    public static ClockListEvent addSavedTime(@NonNull final String timeZoneId, int hour, int minute) {
        return new ClockListEvent(Event.ADD_SAVED_TIME, timeZoneId, hour, minute);
    }

    public static ClockListEvent deleteSavedTime(@NonNull final String timeZoneId, final int savedTimePosition) {
        return new ClockListEvent(Event.DELETE_SAVED_TIME, timeZoneId, savedTimePosition);
    }

    public static ClockListEvent addAlarm(@NonNull final String timeString, final String tag) {
        return new ClockListEvent(Event.ADD_ALARM, timeString, tag);
    }

    public static ClockListEvent getLocalTimeZone() {
        return new ClockListEvent(Event.GET_LOCAL_TIMEZONE);
    }

    public static ClockListEvent getOffsetString(@NonNull final String timeZoneId) {
        return new ClockListEvent(Event.GET_OFFSET_STRING, timeZoneId);
    }

    public static ClockListEvent fabClick() {
        return new ClockListEvent(Event.FAB_CLICK);
    }

    public Event getEvent() {
        return event;
    }

    public String getTimezoneId() {
        return bundle.getString(TIMEZONEID);
    }

    public int getSavedTimePosition() {
        return bundle.getInt(TIMEPOSN);
    }

    public int getHour() {
        return bundle.getInt(HOUR);
    }

    public int getMinute() {
        return bundle.getInt(MINUTE);
    }

    public String getTimestring() {
        return bundle.getString(TIMESTR);
    }

    public String getTag() {
        return bundle.getString(TAG);
    }

    @Override
    public String toString() {
        return "ClockListEvent { " + event + "}";
    }

    public enum Event {
        LOAD_CLOCKS,
        DELETE_CLOCK,
        ADD_SAVED_TIME,
        DELETE_SAVED_TIME,
        ADD_ALARM,
        GET_MILLIS_OF_DAY,
        GET_LOCAL_TIMEZONE,
        GET_OFFSET_STRING,
        GET_FORMATTED_TIME_STRINGS,
        FAB_CLICK
    }
}