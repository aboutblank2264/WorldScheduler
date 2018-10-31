package com.aboutblank.worldscheduler.backend.time;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.List;

public interface TimeService {

    DateTimeZone getTimeZone(String id);

    List<String> getCityNames();

    DateTime getCurrentAtTimeZone(String id);

    DateTime convertTimeAtTimeZone(LocalDateTime time, String targetZoneId);

    long getOffset(String timeZoneId);

    String getOffsetString(String timeZoneId);
}
