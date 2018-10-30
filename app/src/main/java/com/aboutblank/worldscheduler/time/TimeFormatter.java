package com.aboutblank.worldscheduler.time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TimeFormatter {

    private String[] INVALID_STRINGS = {"Etc"};

    public List<String> formatList(Set<String> cities) {
        List<String> ret = new ArrayList<>();

        for (String city : cities) {
            checkForInvalid(ret, city);
        }

        Collections.sort(ret);

        return ret;
    }

    private void checkForInvalid(List<String> list, String city) {
        for (String invalid : INVALID_STRINGS) {
            if (city.contains(invalid)) {
                break;
            }
        }

        list.add(city);
    }
}
