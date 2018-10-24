package com.aboutblank.worldscheduler.backend;

import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.backend.room.LocalDatabase;

import java.util.List;

public class DataServiceImpl implements DataService {
    private LocalDatabase localDatabase;

    public DataServiceImpl(LocalDatabase localDatabase) {
        this.localDatabase = localDatabase;
    }

    @Override
    public Clock getClockById(String timeZoneId) {
        return localDatabase.getClockById(timeZoneId);
    }

    @Override
    public List<Clock> getAllClocks() {
        return localDatabase.getAllClocks();
    }

    @Override
    public void saveClock(String timeZoneId) {
        localDatabase.saveClock(timeZoneId);
    }

    @Override
    public void deleteClock(String timeZoneId) {
        localDatabase.deleteClock(timeZoneId);
    }
}
