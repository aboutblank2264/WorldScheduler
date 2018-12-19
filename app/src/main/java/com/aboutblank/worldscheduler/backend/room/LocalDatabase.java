package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {SavedTime.class, SavedTimeZone.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract SavedTimeDao savedTimeDao();
    public abstract SavedTimeZoneDao savedTimeZoneDao();

    public static LocalDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, LocalDatabase.class, LocalDatabase.class.getName())
                .fallbackToDestructiveMigration()
                .build();
    }
}
