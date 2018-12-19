package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity
public class SavedTimeZone {

    @PrimaryKey
    @NonNull
    public String timeZoneId;

    public SavedTimeZone(@NonNull final String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    @NonNull
    public String getTimeZoneId() {
        return timeZoneId;
    }

    @Override
    public String toString() {
        return "SavedTimeZone{" +
                "timeZoneId='" + timeZoneId + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedTimeZone that = (SavedTimeZone) o;
        return Objects.equals(timeZoneId, that.timeZoneId);
    }
}
