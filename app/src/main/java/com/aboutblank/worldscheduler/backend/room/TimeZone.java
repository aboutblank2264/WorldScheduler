package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class TimeZone {
    @PrimaryKey
    @NonNull
    private String id;

    public TimeZone(@NonNull final String id) {
        this.id = id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    //NOTE: this is kind of a hack to get AutoCompleteTextView to work as quickly as possible.
    // https://stackoverflow.com/questions/13063849/using-android-autocompletetextview-with-arrayadapterobjects-instead-of-arrayad
    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == TimeZone.class) {
            TimeZone t = (TimeZone) obj;

            return t.getId().equals(id);
        }
        return false;
    }
}
