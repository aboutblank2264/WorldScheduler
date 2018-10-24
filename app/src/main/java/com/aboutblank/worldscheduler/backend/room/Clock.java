package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Clock {

    @ColumnInfo
    @PrimaryKey
    private String timeZoneId;

    public Clock(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }
}
