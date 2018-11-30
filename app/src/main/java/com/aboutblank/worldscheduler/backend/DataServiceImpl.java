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
    public Clock getLocalClock() {
        return new Clock(DateTimeZone.getDefault().getID());
    }

    @Override
    public String getTimeDifference(String timeZoneId) {
       return TimeFormatter.getTimeDifference(timeZoneId);
    }

    @Override
    public long toMillisOfDay(int hour, int minute) {
        return TimeFormatter.toMillisOfDay(hour, minute);
    }

    @Override
    public void addSavedTimeToClock(@NonNull final String timeZoneId, long millisOfDay) {
        Clock clock = clockDao.getClockById(timeZoneId);
        clock.addSavedTime(millisOfDay);
        clockDao.update(clock);
    }

    @Override
    public void addSavedTimeToClock(@NonNull final String timeZoneId, int hour, int minute) {
        addSavedTimeToClock(timeZoneId, toMillisOfDay(hour, minute));
    }

    @Override
    public void deleteSavedTimeFromClock(@NonNull final String timeZoneId, int position) {
        Clock clock = clockDao.getClockById(timeZoneId);
        clock.getSavedTimes().remove(position);
        clockDao.update(clock);
    }

    @Override
    public String[] getFormattedTimeStrings(@NonNull final String timeZoneId, final long savedTime) {
        String[] res = new String[2];
        res[0] = TimeFormatter.toClockString(savedTime);
        res[1] = TimeFormatter.toClockString(TimeFormatter.toMillisOfTimeZone(savedTime, timeZoneId));

        return  res;
    }
}
