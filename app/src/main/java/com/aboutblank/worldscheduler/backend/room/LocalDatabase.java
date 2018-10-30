package com.aboutblank.worldscheduler.backend.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.List;

@Database(entities = {Clock.class}, version = 2)
public abstract class LocalDatabase extends RoomDatabase {
    abstract ClockDao clockDao();

    public static LocalDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, LocalDatabase.class, LocalDatabase.class.getName())
                .fallbackToDestructiveMigration()
                .build();
    }

    public Clock getClockById(String timeZoneId) {
        return clockDao().getClockById(timeZoneId);
    }

    public List<Clock> getAllClocks() {
        return clockDao().getAllClocks();
    }

    public LiveData<List<Clock>> getAllClocksLive() {
        return clockDao().getAllClocksLive();
    }

    public void saveClock(String timeZoneId) {
        clockDao().insertClock(new Clock(timeZoneId));
    }

    public void deleteClock(String timeZoneId) {
        clockDao().deleteClock(timeZoneId);
    }
}
