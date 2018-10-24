package com.aboutblank.worldscheduler.time;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.Set;

public interface TimeService {

    DateTimeZone getTimeZone(String id);

    Set<String> getSetTimeZoneNames();

    DateTime getCurrentAtTimeZone(String id);

    DateTime convertTimeAtTimeZone(LocalDateTime time, String targetZoneId);
}
