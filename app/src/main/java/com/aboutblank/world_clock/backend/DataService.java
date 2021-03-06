package com.aboutblank.world_clock.backend;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.aboutblank.world_clock.backend.room.Clock;
import com.aboutblank.world_clock.backend.time.TimeZone;

import java.util.List;

public interface DataService {

    Clock getClockById(String timeZoneId);

    LiveData<List<Clock>> getAllClocksLive();

    List<TimeZone> getTimeZones();

    void saveClockWithId(String timeZoneId);

    void deleteClock(String timeZoneId);

    String getLocalTimeZone();

    String getTimeDifference(String timeZoneId);

    long toMillisOfDay(int hour, int minute);

    void addSavedTime(@NonNull String timeZoneId, int hour, int minute);

    List<Long> getSavedTimes(@NonNull String timeZoneId);

    void changeSavedTime(@NonNull String timeZoneId, int hour, int minute, long oldSavedTime);

    void changeSavedTimeWithTimeZoneMillis(@NonNull String timeZoneId, int hour, int minute, long oldSavedTime);

    void deleteSavedTime(@NonNull String timeZoneId, long savedTime);

    String[] getFormattedTimeStrings(@NonNull String timeZoneId, long savedTime);

    long getMillisFromTimeString(@NonNull String timeString);

    int getHourOfDay(long millis);

    int getMinuteOfHour(long millis);
}
