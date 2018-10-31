package com.aboutblank.worldscheduler.backend.time;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.List;

public class TimeServiceImpl implements TimeService {
    private final static long MILLIS_TO_HOUR = 3600000;
    private final static long MILLIS_TO_HALF = MILLIS_TO_HOUR / 2;

    private final static String AHEAD = "hours ahead";
    private final static String BEHIND = "hours behind";

    private List<String> cityNames;

    @Override
    public DateTimeZone getTimeZone(String id) {
        return DateTimeZone.forID(id);
    }

    @Override
    public List<String> getCityNames() {
        if(cityNames == null) {
            cityNames = TimeFormatter.formatList(DateTimeZone.getAvailableIDs());
        }
        return cityNames;
    }

    @Override
    public DateTime getCurrentAtTimeZone(String targetZoneId) {
        return LocalDateTime.now(DateTimeZone.forID(targetZoneId)).toDateTime();
    }

    @Override
    public DateTime convertTimeAtTimeZone(LocalDateTime time, String targetZoneId) {
        return time.toDateTime(DateTimeZone.forID(targetZoneId));
    }

    @Override
    public long getOffset(String timeZoneId) {
        DateTimeZone target = DateTimeZone.forID(timeZoneId);
        return DateTimeZone.getDefault().getOffset(DateTime.now(target));
    }

    @Override
    public String getOffsetString(String timeZoneId) {
        long offset = getOffset(timeZoneId);

        long hours = Math.abs(offset / MILLIS_TO_HOUR);
        long remainder = offset % MILLIS_TO_HOUR;

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(hours);

        if (remainder / MILLIS_TO_HALF == 1) {
            strBuilder.append(".")
                    .append(5);
        }
        strBuilder.append(offset > 0 ? AHEAD : BEHIND);

        return strBuilder.toString();
    }
}
