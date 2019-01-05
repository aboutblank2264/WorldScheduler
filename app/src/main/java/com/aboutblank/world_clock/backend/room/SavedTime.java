package com.aboutblank.world_clock.backend.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity/*(indices = {@Index(value = "timeZone", unique = true)},
        foreignKeys = {
                @ForeignKey(entity = SavedTimeZone.class,
                        parentColumns = "timeZoneId",
                        childColumns = "timeZone",
                        onDelete = ForeignKey.CASCADE)})*/
public class SavedTime {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String timeZone;
    public long time;

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
