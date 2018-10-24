package com.aboutblank.worldscheduler.backend.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import java.util.List;

@Database(entities = {Clock.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    private ClockDao clockDao;

    public LiveData<Clock> getClockById(String timeZoneId) {
        return clockDao.getClockById(timeZoneId);
    }

    public LiveData<List<Clock>>  getAllClocks() {
        return clockDao.getAllClocks();
    }

    public void saveClock(String timeZoneId) {
        clockDao.insertClock(new Clock(timeZoneId));
    }

    public void deleteClock(String timeZoneId) {
        clockDao.deleteClock(timeZoneId);
    }
}
