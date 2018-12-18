package com.aboutblank.worldscheduler.backend;

import android.arch.lifecycle.LiveData;
import android.database.SQLException;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.ThreadManager;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.backend.room.ClockDao;
import com.aboutblank.worldscheduler.backend.room.LocalDatabase;
import com.aboutblank.worldscheduler.backend.room.SavedTime;
import com.aboutblank.worldscheduler.backend.room.SavedTimeDao;
import com.aboutblank.worldscheduler.backend.time.TimeFormatter;
import com.aboutblank.worldscheduler.backend.time.TimeZone;

import org.joda.time.DateTimeZone;

import java.util.List;

public class DataServiceImpl implements DataService {

    private ClockDao clockDao;
    private SavedTimeDao savedTimeDao;
    private List<TimeZone> timeZones;

    public DataServiceImpl(LocalDatabase localDatabase, ThreadManager threadManager) {
        this.clockDao = localDatabase.clockDao();
        this.savedTimeDao = localDatabase.savedTimeDao();
        initialize(threadManager);
    }

    private void initialize(ThreadManager threadManager) {
        threadManager.execute(new Runnable() {
            @Override
            public void run() {
                timeZones = TimeFormatter.getListOfTimeZones();
            }
        });
    }

    @Override
    public Clock getClockById(String timeZoneId) {
        return clockDao.getClockById(timeZoneId);
    }

    @Override
    public LiveData<List<Clock>> getAllClocksLive() {
        return clockDao.getAllClocksLive();
    }

    @Override
    public List<TimeZone> getTimeZones() {
        return timeZones;
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
    public void addSavedTime(@NonNull final String timeZoneId, int hour, int minute) throws SQLException {
        savedTimeDao.addTime(new SavedTime(toMillisOfDay(hour, minute), timeZoneId));
    }

    @Override
    public List<Long> getSavedTimes(@NonNull final String timeZoneId) {
        return savedTimeDao.getTimes(timeZoneId);
    }

    @Override
    public void changeSavedTime(@NonNull final String timeZoneId, int hour, int minute, long oldSavedTime) {
        savedTimeDao.updateTime(timeZoneId, oldSavedTime, toMillisOfDay(hour, minute));
    }

    @Override
    public void deleteSavedTime(@NonNull final String timeZoneId, long oldSavedTime) {
        savedTimeDao.delete(timeZoneId, oldSavedTime);
    }

    @Override
    public String[] getFormattedTimeStrings(@NonNull final String timeZoneId, final long savedTime) {
        String[] res = new String[2];
        res[0] = TimeFormatter.toClockString(savedTime);
        res[1] = TimeFormatter.toClockString(TimeFormatter.toMillisOfTimeZone(savedTime, timeZoneId));

        return res;
    }

    @Override
    public long getMillisFromTimeString(@NonNull String timeString) {
        return TimeFormatter.toMillisFromString(timeString);
    }

    @Override
    public int getHourOfDay(long millis) {
        return TimeFormatter.getHour(millis);
    }

    @Override
    public int getMinuteOfHour(long millis) {
        return TimeFormatter.getMinute(millis);
    }
}
