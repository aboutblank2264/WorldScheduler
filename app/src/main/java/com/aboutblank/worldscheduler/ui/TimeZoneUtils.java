package com.aboutblank.worldscheduler.ui;

public abstract class TimeZoneUtils {
    private final static char SPACE = ' ';
    private final static char UNDER = '_';

    public static String format(String timeZoneId) {
        return timeZoneId.replace(UNDER, SPACE);
    }
}
