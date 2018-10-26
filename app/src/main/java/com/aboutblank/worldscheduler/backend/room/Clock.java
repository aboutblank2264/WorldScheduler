package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Clock {

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo
    private String timeZoneId;

    public Clock(@NonNull String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getTimeZoneId() {
        return timeZoneId;
    }

    @Override
    public String toString() {
        return "Clock { " +
                "id = " + id +
                ", timeZoneId = '" + timeZoneId + '\'' +
                '}';
    }
}
