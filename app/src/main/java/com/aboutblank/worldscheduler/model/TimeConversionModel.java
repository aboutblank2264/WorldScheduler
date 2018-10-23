package com.aboutblank.worldscheduler.model;

import org.joda.time.DateTimeZone;

public class TimeConversionModel extends BaseModel {
    private final long currentMillis;

    private final DateTimeZone targetTimeZone;

    public TimeConversionModel(boolean loading, Throwable error, long currentMillis, DateTimeZone targetTimeZone) {
        super(loading, error);
        this.currentMillis = currentMillis;
        this.targetTimeZone = targetTimeZone;
    }
}
