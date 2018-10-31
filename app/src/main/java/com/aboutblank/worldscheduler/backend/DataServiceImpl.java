package com.aboutblank.worldscheduler.backend;

import android.arch.lifecycle.LiveData;

import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.backend.room.LocalDatabase;
import com.aboutblank.worldscheduler.backend.time.TimeService;

import org.joda.time.DateTimeZone;

import java.util.List;

public class DataServiceImpl implements DataService {

    private LocalDatabase localDatabase;
    private TimeService timeService;

    public DataServiceImpl(LocalDatabase localDatabase, final TimeService timeService) {
        this.localDatabase = localDatabase;
        this.timeService = timeService;
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
    public LiveData<List<Clock>> getAllClocksLive() {
        return localDatabase.getAllClocksLive();
    }

    @Override
    public void saveClock(String timeZoneId) {
        localDatabase.saveClock(timeZoneId);
    }

    @Override
    public void deleteClock(String timeZoneId) {
        localDatabase.deleteClock(timeZoneId);
    }

    @Override
    public Clock getLocalClock() {
        return new Clock(DateTimeZone.getDefault().getID());
    }

    @Override
    public long getOffset(String timeZoneId) {
        return timeService.getOffset(timeZoneId);
    }

    @Override
    public String getOffsetString(String timeZoneId) {
       return timeService.getOffsetString(timeZoneId);
    }
}
