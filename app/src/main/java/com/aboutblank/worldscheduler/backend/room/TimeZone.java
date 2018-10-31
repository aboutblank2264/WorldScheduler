package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TimeZone {
    @PrimaryKey
    private String id;
    private String name;

    public TimeZone(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TimeZone { " +
                "zoneId = '" + id + '\'' +
                ", name = '" + name + '\'' +
                '}';
    }
}
