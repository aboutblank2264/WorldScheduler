package com.aboutblank.worldscheduler.backend;

import org.joda.time.DateTimeZone;

import java.util.List;

public interface TimeController {

    DateTimeZone getTimeZone(String id);

    List<DateTimeZone> getAllTimeZones();

    long currentTimeMillis();

}
