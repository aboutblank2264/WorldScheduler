package com.aboutblank.worldscheduler.backend;

import android.arch.lifecycle.LiveData;

import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.backend.room.ClockDao;
import com.aboutblank.worldscheduler.backend.room.LocalDatabase;
import com.aboutblank.worldscheduler.backend.room.TimeZoneDao;
import com.aboutblank.worldscheduler.backend.time.TimeService;

import org.joda.time.DateTimeZone;

import java.util.List;

public class DataServiceImpl implements DataService {

    private ClockDao clockDao;
    private TimeZoneDao timeZoneDao;
    private TimeService timeService;

    public DataServiceImpl(LocalDatabase localDatabase, final TimeService timeService) {
        this.clockDao = localDatabase.clockDao();
        this.timeZoneDao = localDatabase.timeZoneDao();
        this.timeService = timeService;
    }

    @Override
    public Clock getClockById(String timeZoneId) {
        return clockDao.getClockById(timeZoneId);
    }

    @Override
    public List<Clock> getAllClocks() {
        return clockDao.getAllClocks();
    }

    @Override
    public LiveData<List<Clock>> getAllClocksLive() {
        return clockDao.getAllClocksLive();
    }

    @Override
    public List<String> getCityNames() {
        return timeService.getCityNames();
    }

    @Override
    public void saveClockWithName(String name) {
        clockDao.insertClock(new Clock(timeZoneDao.getTimeZoneIdByName(name)));
    }

    @Override
    public void deleteClock(String timeZoneId) {
        clockDao.deleteClock(timeZoneId);
    }

    @Override
    public Clock getLocalClock() {
        return new Clock(DateTimeZone.getDefault().getID());
    }

    @Override
    public String getTimeDifference(String timeZoneId) {
       return timeService.getTimeDifference(timeZoneId);
    }
}
