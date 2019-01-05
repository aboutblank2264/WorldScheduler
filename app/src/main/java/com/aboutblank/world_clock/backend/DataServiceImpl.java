package com.aboutblank.world_clock.backend;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.aboutblank.world_clock.ThreadManager;
import com.aboutblank.world_clock.backend.room.Clock;
import com.aboutblank.world_clock.backend.room.LocalDatabase;
import com.aboutblank.world_clock.backend.room.SavedTime;
import com.aboutblank.world_clock.backend.room.SavedTimeDao;
import com.aboutblank.world_clock.backend.room.SavedTimeZone;
import com.aboutblank.world_clock.backend.room.SavedTimeZoneDao;
import com.aboutblank.world_clock.backend.time.TimeFormatter;
import com.aboutblank.world_clock.backend.time.TimeZone;

import org.joda.time.DateTimeZone;

import java.util.List;

public class DataServiceImpl implements DataService {

//    private ClockDao clockDao;
    private SavedTimeDao savedTimeDao;
    private SavedTimeZoneDao savedTimeZoneDao;
    private List<TimeZone> timeZones;

    public DataServiceImpl(LocalDatabase localDatabase, ThreadManager threadManager) {
//        this.clockDao = localDatabase.clockDao();
        this.savedTimeDao = localDatabase.savedTimeDao();
        this.savedTimeZoneDao = localDatabase.savedTimeZoneDao();
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
        return savedTimeZoneDao.getClockById(timeZoneId);
    }

    @Override
    public LiveData<List<Clock>> getAllClocksLive() {
        return savedTimeZoneDao.getAllClocksLive();
    }

    @Override
    public List<TimeZone> getTimeZones() {
        return timeZones;
    }

    @Override
    public void saveClockWithId(String timeZoneId) {
        savedTimeZoneDao.insert(new SavedTimeZone(timeZoneId));
    }

    @Override
    public void deleteClock(String timeZoneId) {
        savedTimeZoneDao.delete(timeZoneId);
        savedTimeDao.delete(timeZoneId);
    }

    @Override
    public String getLocalTimeZone() {
        return DateTimeZone.getDefault().getID();
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
    public void addSavedTime(@NonNull final String timeZoneId, int hour, int minute) {
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
    public void changeSavedTimeWithTimeZoneMillis(@NonNull final String timeZoneId, int hour, int minute, long oldSavedTime) {
        long millis = TimeFormatter.convertMillisOfDayFromTimeZoneToDefault(toMillisOfDay(hour, minute), timeZoneId);
        savedTimeDao.updateTime(timeZoneId, oldSavedTime, millis);
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
