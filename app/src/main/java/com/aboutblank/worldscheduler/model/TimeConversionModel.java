package com.aboutblank.worldscheduler.model;

import com.aboutblank.worldscheduler.ui.screenstates.ScreenState;

public class TimeConversionModel extends BaseModel {
    private final long currentMillis;
    private final String timeZoneId;

    public TimeConversionModel(long currentMillis,
                               @ScreenState.State int state,
                               String timeZoneId,
                               Throwable error) {
        super(state, error);
        this.currentMillis = currentMillis;
        this.timeZoneId = timeZoneId;
    }

    public long getCurrentMillis() {
        return currentMillis;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }
}
