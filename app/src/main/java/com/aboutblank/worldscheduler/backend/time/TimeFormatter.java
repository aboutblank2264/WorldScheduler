package com.aboutblank.worldscheduler.backend.time;

import com.aboutblank.worldscheduler.backend.room.TimeZone;

import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class TimeFormatter {
    private static final char SLASH = '/';
    private static String[] INVALID_STRINGS = {"Etc"};

    static List<String> formatList(Set<String> cities) {
        List<String> ret = new ArrayList<>();

        for (String city : cities) {
            checkForInvalid(ret, city);
        }

        Collections.sort(ret);

        return ret;
    }

    static List<TimeZone> getListOfTimeZones() {
        List<TimeZone> timeZoneList = new ArrayList<>();

        for(String id : DateTimeZone.getAvailableIDs()) {
            if(checkIfValid(id)) {
                timeZoneList.add(new TimeZone(id, TimeFormatter.formatTimeZoneId(id)));
            }
        }

        return timeZoneList;
    }

    private static void checkForInvalid(List<String> list, String city) {
        for (String invalid : INVALID_STRINGS) {
            if (city.contains(invalid)) {
                break;
            }
        }

        list.add(city);
    }

    private static boolean checkIfValid(String city) {
        for(String invalid : INVALID_STRINGS) {
            if(city.contains(invalid)) {
                return false;
            }
        }

        return true;
    }

    public static String formatTimeZoneId(String timeZoneId) {
        return timeZoneId.substring(timeZoneId.indexOf(SLASH) + 1);
    }
}
