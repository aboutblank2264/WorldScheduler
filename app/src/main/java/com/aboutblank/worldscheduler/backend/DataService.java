package com.aboutblank.worldscheduler.backend;

import com.aboutblank.worldscheduler.backend.room.Clock;

import java.util.List;

public interface DataService {

    Clock getClockById(String timeZoneId);

    List<Clock> getAllClocks();

    void saveClock(String timeZoneId);

    void deleteClock(String timeZoneId);

    Clock getLocalClock();
}
