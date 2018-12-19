package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SavedTime {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public long time;
    public String timeZone;

    public SavedTime(final long time, final String timeZone) {
        this.id = 0;
        this.time = time;
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return "SavedTime{" +
                "time=" + time +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
