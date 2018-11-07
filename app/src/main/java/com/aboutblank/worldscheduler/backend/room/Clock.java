package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.backend.time.TimeFormatter;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clock clock = (Clock) o;
        return id == clock.id &&
                Objects.equals(timeZoneId, clock.timeZoneId);
    }
}
