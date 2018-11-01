package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class TimeZone {
    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String name;

    public TimeZone(@NonNull String id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
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

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == TimeZone.class) {
            TimeZone t = (TimeZone) obj;

            return t.getId().equals(id) && t.getName().equals(name);
        }
        return false;
    }
}
