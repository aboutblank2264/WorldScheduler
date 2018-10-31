package com.aboutblank.worldscheduler.backend.time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class TimeFormatter {
    private static final char SLASH = '/';
    private static String[] INVALID_STRINGS = {"Etc"};

    public static List<String> formatList(Set<String> cities) {
        List<String> ret = new ArrayList<>();

        for (String city : cities) {
            checkForInvalid(ret, city);
        }

        Collections.sort(ret);

        return ret;
    }

    private static void checkForInvalid(List<String> list, String city) {
        for (String invalid : INVALID_STRINGS) {
            if (city.contains(invalid)) {
                break;
            }
        }

        list.add(formatTimeZoneId(city));
    }

    public static String formatTimeZoneId(String timeZoneId) {
        return timeZoneId.substring(timeZoneId.indexOf(SLASH) + 1);
    }
}
