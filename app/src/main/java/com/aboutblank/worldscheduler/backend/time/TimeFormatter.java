package com.aboutblank.worldscheduler.backend.time;

import com.aboutblank.worldscheduler.backend.room.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class TimeFormatter {
    private final static long MILLIS_TO_HOUR = (60 * 60 * 1000);
    private final static long MILLIS_TO_HALF = (60 * 1000);

    private static final char SLASH = '/';
    private static final char UNDER = '_';
    private static final char SPACE = ' ';

    private final static String AHEAD = "hours ahead";
    private final static String BEHIND = "hours behind";

    private final static String TIME_FORMAT = "hh:mm aa";

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(TIME_FORMAT);

    public static List<String> formatList(Set<String> cities) {
        List<String> ret = new ArrayList<>();

        for (String city : cities) {
            if (checkIfValid(city)) {
                ret.add(city);
            }
        }

        Collections.sort(ret);
        return ret;
    }

    public static List<TimeZone> getListOfTimeZones() {
        return convertToTimeZone(DateTimeZone.getAvailableIDs());
    }

    public static List<TimeZone> convertToTimeZone(Set<String> timeZoneIds) {
        List<TimeZone> timeZoneList = new ArrayList<>();

        for (String id : timeZoneIds) {
            if (checkIfValid(id)) {
                timeZoneList.add(new TimeZone(id));
            }
        }

        return timeZoneList;
    }

    private static boolean checkIfValid(String city) {
        return city.indexOf(SLASH) > 0;
    }

    public static String getTimeDifference(String timeZoneId) {
        return getTimeDifference(DateTimeZone.getDefault().getID(), timeZoneId);
    }

    public static String getTimeDifference(String nowId, String targetId) {
        long current = DateTime.now().getMillis();
        return getTimeDifferenceWithTime(nowId, targetId, current);
    }

    public static String getTimeDifferenceWithTime(String nowId, String targetId, long millis) {
        DateTimeZone nowZone = DateTimeZone.forID(nowId);
        DateTimeZone targetZone = DateTimeZone.forID(targetId);
        long current = new LocalDateTime(millis, nowZone).toDateTime().getMillis();
        long target = new LocalDateTime(millis, targetZone).toDateTime().getMillis();

        long timeDiff = current - target;

        long hoursDifference = timeDiff / MILLIS_TO_HOUR % 24;
        long remainder = timeDiff / MILLIS_TO_HALF % 60;

        return String.valueOf(Math.abs(hoursDifference)) +
                (Math.abs(remainder) >= 30 ? ".5 " : " ") +
                (hoursDifference > 0 ? AHEAD : BEHIND);
    }

    public static long toMillisOfDay(int hour, int minute) {
        return new LocalTime(hour, minute).getMillisOfDay();
    }

    public static long toMillisOfTimeZone(long millisOfDay, String timeZoneId) {
        return new LocalTime(millisOfDay).toDateTimeToday(DateTimeZone.forID(timeZoneId)).getMillisOfDay();
    }

    public static String toClockString(long millisOfDay) {
        return LocalTime.fromMillisOfDay(millisOfDay).toString(TIME_FORMAT);
    }

    public static long toMillisFromString(String clockString) {
        return LocalTime.parse(clockString, DATE_TIME_FORMATTER).getMillisOfDay();
    }

    public static int getHour(long millisOfDay) {
        return LocalTime.fromMillisOfDay(millisOfDay).getHourOfDay();
    }

    public static int getMinute(long millisOfDay) {
        return LocalTime.fromMillisOfDay(millisOfDay).getMinuteOfHour();
    }
}
