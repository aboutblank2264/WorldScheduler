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

    void deleteClock(@NonNull Clock clock);

    Clock getLocalClock();

    String getTimeDifference(String timeZoneId);
}
