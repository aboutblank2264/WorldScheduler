package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = "timeZoneId", unique = true)},
        foreignKeys = {
                @ForeignKey(entity = Clock.class, parentColumns = "timeZoneId", childColumns = "timeZoneId", onDelete = ForeignKey.CASCADE)})
public class SavedTime {
    @PrimaryKey
    private long time;

    private String timeZoneId;

    public SavedTime(final long time, final String timeZoneId) {
        this.time = time;
        this.timeZoneId = timeZoneId;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(final String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "SavedTime = " + time;
    }
}
