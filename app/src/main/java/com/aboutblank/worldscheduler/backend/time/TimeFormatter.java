package com.aboutblank.worldscheduler.backend.time;

import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.backend.room.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

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

    private static String[] INVALID_STRINGS = {"Etc"};

    public static List<String> formatList(Set<String> cities) {
        List<String> ret = new ArrayList<>();

        for (String city : cities) {
            if (checkIfValid(city)) {
                ret.add(TimeFormatter.formatTimeZoneId(city));
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
                timeZoneList.add(new TimeZone(id, TimeFormatter.formatTimeZoneId(id)));
            }
        }

        return timeZoneList;
    }

    private static boolean checkIfValid(String city) {
        if (city.indexOf(SLASH) < 0) {
            return false;
        }
        for (String invalid : INVALID_STRINGS) {
            if (city.contains(invalid)) {
                return false;
            }
        }

        return true;
    }

    public static String formatTimeZoneId(@NonNull String timeZoneId) {
        int lastSlashIndex = timeZoneId.lastIndexOf(SLASH);

        return timeZoneId.substring(lastSlashIndex + 1).replace(UNDER, SPACE);
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
}
