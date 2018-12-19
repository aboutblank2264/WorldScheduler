package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class Clock {
    @Embedded
    public SavedTimeZone timeZoneId;

    @Relation(parentColumn = "timeZoneId",
            entityColumn = "timeZone",
            entity = SavedTime.class,
    projection = {"time"})
    public List<Long> savedTimes;


    public String getTimeZoneId() {
        return timeZoneId.getTimeZoneId();
    }

    public List<Long> getSavedTimes() {
      return savedTimes;
    }

    @Override
    public String toString() {
        return "Clock{" +
                "timeZoneId=" + timeZoneId +
                ", savedTimes=" + savedTimes +
                '}';
    }
}
