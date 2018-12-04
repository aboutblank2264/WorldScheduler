package com.aboutblank.worldscheduler.backend;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.backend.room.TimeZone;

import java.util.List;

public interface DataService {

    Clock getClockById(String timeZoneId);

    Clock getClockByName(String name);

    LiveData<List<Clock>> getAllClocksLive();

    List<String> getCityNames();

    List<TimeZone> getTimeZones();

    void saveClockWithId(String timeZoneId);

    void deleteClock(String timeZoneId);

    Clock getLocalClock();

    String getTimeDifference(String timeZoneId);

    long toMillisOfDay(int hour, int minute);

    void addSavedTimeToClock(@NonNull String timeZoneId, long millisOfDay);

    void addSavedTimeToClock(@NonNull String timeZoneId, int hour, int minute);

    void deleteSavedTimeFromClock(@NonNull String timeZoneId, int position);

    String[] getFormattedTimeStrings(@NonNull String timeZoneId, long savedTime);

    long getMillisFromTimeString(@NonNull String timeString);

    int getHourOfDay(long millis);

    int getMinuteOfHour(long millis);
}
