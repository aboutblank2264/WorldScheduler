package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Clock {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey
    private String timeZoneId;

    public Clock(@NonNull String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }
}
