package com.aboutblank.worldscheduler.time;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.List;

public class TimeServiceImpl implements TimeService {
    private TimeFormatter timeFormatter;

    public TimeServiceImpl() {
        timeFormatter = new TimeFormatter();
    }

    @Override
    public DateTimeZone getTimeZone(String id) {
        return DateTimeZone.forID(id);
    }

    @Override
    public List<String> getCityNames() {
        return timeFormatter.formatList(DateTimeZone.getAvailableIDs());
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
