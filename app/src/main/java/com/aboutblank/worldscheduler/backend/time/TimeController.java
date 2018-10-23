package com.aboutblank.worldscheduler.backend.time;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.Set;

public interface TimeController {

    DateTimeZone getTimeZone(String id);

    Set<String> getSetTimeZoneNames();

    DateTime getCurrentAtTimeZone(String id);

    DateTime convertTimeAtTimeZone(LocalDateTime time, String targetZoneId);
}
