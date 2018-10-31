package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.backend.time.TimeFormatter;

@Entity
public class Clock {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
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

    public String getName() {
        return TimeFormatter.formatTimeZoneId(timeZoneId);
    }

    @Override
    public String toString() {
        return "Clock { " +
                "id = " + id +
                ", timeZoneId = '" + timeZoneId + '\'' +
                '}';
    }
}
