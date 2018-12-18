package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Clock.class, SavedTime.class}, version = 6)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract ClockDao clockDao();
    public abstract SavedTimeDao savedTimeDao();

    public static LocalDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, LocalDatabase.class, LocalDatabase.class.getName())
                .fallbackToDestructiveMigration()
                .build();
    }
}
