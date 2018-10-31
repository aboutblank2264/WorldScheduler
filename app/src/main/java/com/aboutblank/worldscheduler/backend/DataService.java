package com.aboutblank.worldscheduler.backend;

import android.arch.lifecycle.LiveData;

import com.aboutblank.worldscheduler.backend.room.Clock;

import java.util.List;

public interface DataService {

    Clock getClockById(String timeZoneId);

    List<Clock> getAllClocks();

    LiveData<List<Clock>> getAllClocksLive();

    List<String> getCityNames();

    void saveClockWithName(String timeZoneId);

    void deleteClock(String timeZoneId);

    Clock getLocalClock();

    String getTimeDifference(String timeZoneId);
}
