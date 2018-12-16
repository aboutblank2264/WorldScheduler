package com.aboutblank.worldscheduler.backend;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.backend.room.TimeZone;

import java.util.List;

public interface DataService {

    Clock getClockById(String timeZoneId);

    LiveData<List<Clock>> getAllClocksLive();

    List<TimeZone> getTimeZones();

    void saveClockWithId(String timeZoneId);

    void deleteClock(String timeZoneId);

    Clock getLocalClock();

    String getTimeDifference(String timeZoneId);

    long toMillisOfDay(int hour, int minute);

    void addSavedTime(@NonNull String timeZoneId, int hour, int minute);

    List<Long> getSavedTimes(@NonNull String timeZoneId);

    void changeSavedTime(@NonNull String timeZoneId, int hour, int minute, long oldSavedTime);

    void deleteSavedTime(@NonNull String timeZoneId, long savedTime);

    String[] getFormattedTimeStrings(@NonNull String timeZoneId, long savedTime);

    long getMillisFromTimeString(@NonNull String timeString);

    int getHourOfDay(long millis);

    int getMinuteOfHour(long millis);
}
