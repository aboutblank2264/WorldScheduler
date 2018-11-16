package com.aboutblank.worldscheduler.backend;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.ThreadManager;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.backend.room.ClockDao;
import com.aboutblank.worldscheduler.backend.room.LocalDatabase;
import com.aboutblank.worldscheduler.backend.room.TimeZone;
import com.aboutblank.worldscheduler.backend.room.TimeZoneDao;
import com.aboutblank.worldscheduler.backend.time.TimeFormatter;

import org.joda.time.DateTimeZone;

import java.util.List;

public class DataServiceImpl implements DataService {

    private ClockDao clockDao;
    private TimeZoneDao timeZoneDao;

    public DataServiceImpl(LocalDatabase localDatabase, ThreadManager threadManager) {
        this.clockDao = localDatabase.clockDao();
        this.timeZoneDao = localDatabase.timeZoneDao();
        initialize(threadManager);
    }

    private void initialize(ThreadManager threadManager) {
        threadManager.execute(new Runnable() {
            @Override
            public void run() {
                if (timeZoneDao.count() == 0) {
                    timeZoneDao.insert(TimeFormatter.getListOfTimeZones());
                }
            }
        });
    }

    @Override
    public Clock getClockById(String timeZoneId) {
        return clockDao.getClockById(timeZoneId);
    }

    @Override
    public Clock getClockByName(String name) {
        String timeZone = timeZoneDao.getTimeZoneIdByName(name);
        return clockDao.getClockById(timeZone);
    }

    @Override
    public LiveData<List<Clock>> getAllClocksLive() {
        return clockDao.getAllClocksLive();
    }

    @Override
    public List<String> getCityNames() {
        return timeZoneDao.getTimeZoneNames();
    }

    @Override
    public List<TimeZone> getTimeZones() {
        return timeZoneDao.getTimeZones();
    }

    @Override
    public void saveClockWithId(String timeZoneId) {
        clockDao.insertClock(new Clock(timeZoneId));
    }

    @Override
    public void deleteClock(String timeZoneId) {
        clockDao.deleteClock(timeZoneId);
    }

    @Override
    public void deleteClock(@NonNull final Clock clock) {
        clockDao.deleteClock(clock);
    }

    @Override
    public Clock getLocalClock() {
        return new Clock(DateTimeZone.getDefault().getID());
    }

    @Override
    public String getTimeDifference(String timeZoneId) {
       return TimeFormatter.getTimeDifference(timeZoneId);
    }
}
