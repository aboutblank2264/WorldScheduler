package com.aboutblank.worldscheduler;

import com.aboutblank.worldscheduler.backend.room.TimeZone;
import com.aboutblank.worldscheduler.backend.time.TimeFormatter;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimeFormatterTest {

    private Set<String> citySet;

    private String taipei = "Asia/Taipei";
    private String la = "America/Los_Angeles";
    private String stjohns = "America/St_Johns";

    private String foobar = "foo/bar";

    private String[] cities = {"Etc/GMT-2",
            "Pacific/Apia",
            "HST",
            "CST6CDT",
            "America/Grand_Turk",
            "America/Indiana/Indianapolis"};

    @Before
    public void init() {
        citySet = new HashSet<>(Arrays.asList(cities));
    }

    @Test
    public void formatList() {
        List<String> formattedCities = TimeFormatter.formatList(citySet);

        System.out.println(formattedCities.toString());

        assertEquals(formattedCities.size(), 3);
        assertFalse(formattedCities.contains("HST"));

        assertEquals("Apia", formattedCities.get(0));
    }

    @Test
    public void convertToTimeZone() {
        List<TimeZone> timezones = TimeFormatter.convertToTimeZone(citySet);

        System.out.println(timezones.toString());

        assertEquals(timezones.size(), 3);
        assertFalse(timezones.contains(new TimeZone("HST", "HST")));
        assertTrue(timezones.contains(new TimeZone("Pacific/Apia", "Apia")));
        assertTrue(timezones.contains(new TimeZone("America/Indiana/Indianapolis", "Indianapolis")));
    }

    @Test
    public void formatTimeZoneId() {
        assertEquals(TimeFormatter.formatTimeZoneId(""), "");
        assertEquals(TimeFormatter.formatTimeZoneId(foobar), "bar");
        assertEquals(TimeFormatter.formatTimeZoneId("Pacific/Apia"), "Apia");
        assertEquals(TimeFormatter.formatTimeZoneId("America/Grand_Turk"), "Grand Turk");
        assertEquals(TimeFormatter.formatTimeZoneId("America/Indiana/Indianapolis"), "Indianapolis");
    }

    @Test
    public void getTimeDifference() {
        String timeDifference = TimeFormatter.getTimeDifference(la, taipei);
        assertEquals(timeDifference, "15 hours behind");

        String timeDifference2 = TimeFormatter.getTimeDifference(la, stjohns);
        assertEquals(timeDifference2, "4.5 hours behind");
    }
}