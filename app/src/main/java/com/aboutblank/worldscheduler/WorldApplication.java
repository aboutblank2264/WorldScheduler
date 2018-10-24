package com.aboutblank.worldscheduler;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.aboutblank.worldscheduler.backend.room.LocalDatabase;

import net.danlew.android.joda.JodaTimeAndroid;

public class WorldApplication extends Application {
    private LocalDatabase localDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

        localDatabase = Room.databaseBuilder(this, LocalDatabase.class, LocalDatabase.class.getName())
                .fallbackToDestructiveMigration()
                .build();
    }

    public LocalDatabase getLocalDatabase() {
        return localDatabase;
    }
}
