package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import java.util.List;

@Database(entities = {Clock.class}, version = 2)
public abstract class LocalDatabase extends RoomDatabase {
    abstract ClockDao clockDao();

    public Clock getClockById(String timeZoneId) {
        return clockDao().getClockById(timeZoneId);
    }

    public List<Clock> getAllClocks() {
        return clockDao().getAllClocks();
    }

    public void saveClock(String timeZoneId) {
        clockDao().insertClock(new Clock(timeZoneId));
    }

    public void deleteClock(String timeZoneId) {
        clockDao().deleteClock(timeZoneId);
    }
}
