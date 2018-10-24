package com.aboutblank.worldscheduler.time;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.Set;

public class TimeServiceImpl implements TimeService {
    public TimeServiceImpl() {
    }

    @Override
    public DateTimeZone getTimeZone(String id) {
        return DateTimeZone.forID(id);
    }

    @Override
    public Set<String> getSetTimeZoneNames() {
        return DateTimeZone.getAvailableIDs(); //TODO do some proper cleaning up of this list
    }

    @Override
    public DateTime getCurrentAtTimeZone(String targetZoneId) {
        return LocalDateTime.now(DateTimeZone.forID(targetZoneId)).toDateTime();
    }

    @Override
    public DateTime convertTimeAtTimeZone(LocalDateTime time, String targetZoneId) {
        return time.toDateTime(DateTimeZone.forID(targetZoneId));
    }
}
