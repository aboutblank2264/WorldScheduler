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
    private String newyork = "America/New_York";
    private String honolulu = "Pacific/Honolulu";

    private String foobar = "foo/bar";

    //America/Los_Angeles  March 15, 2018, 4:00:00 am (Daylight Savings Started)
    private long LA_DaylightSavingsOn = Long.valueOf("1521111600000");

    //America/Los_Angeles November 15, 2018, 4:00:00 am (Daylight Savings Ended)
    private long LA_DaylightSavingsOff = Long.valueOf("1542283200000");

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
    public void printListOfTimeZones() {
        System.out.println(TimeFormatter.getListOfTimeZones().toString());
//        System.out.println(DateTimeZone.getAvailableIDs().toString());
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
        assertFalse(timezones.contains(new TimeZone("HST")));
        assertTrue(timezones.contains(new TimeZone("Pacific/Apia")));
        assertTrue(timezones.contains(new TimeZone("America/Indiana/Indianapolis")));
    }

    @Test
    public void formatTimeZoneId() {
        assertEquals((""), "");
        assertEquals((foobar), "bar");
        assertEquals(("Pacific/Apia"), "Apia");
        assertEquals(("America/Grand_Turk"), "Grand Turk");
        assertEquals(("America/Indiana/Indianapolis"), "Indianapolis");
    }

    @Test
    public void getTimeDifference_WithDaylight() {
        String la_taipei = TimeFormatter.getTimeDifferenceWithTime(la, taipei, LA_DaylightSavingsOn);
        assertEquals(la_taipei, "15 hours behind");

        String taipei_la = TimeFormatter.getTimeDifferenceWithTime(taipei, la, LA_DaylightSavingsOn);
        assertEquals(taipei_la, "15 hours ahead");

        String la_stjohns = TimeFormatter.getTimeDifferenceWithTime(la, stjohns, LA_DaylightSavingsOn);
        assertEquals(la_stjohns, "4.5 hours behind");

        String la_newyork = TimeFormatter.getTimeDifferenceWithTime(la, newyork, LA_DaylightSavingsOn);
        assertEquals(la_newyork, "3 hours behind");

        String la_honolulu = TimeFormatter.getTimeDifferenceWithTime(la, honolulu, LA_DaylightSavingsOn);
        assertEquals(la_honolulu, "3 hours ahead");
    }

    @Test
    public void getTimeDifference_WithOutDaylight() {
        String la_taipei = TimeFormatter.getTimeDifferenceWithTime(la, taipei, LA_DaylightSavingsOff);
        assertEquals(la_taipei, "16 hours behind");

        String taipei_la = TimeFormatter.getTimeDifferenceWithTime(taipei, la, LA_DaylightSavingsOff);
        assertEquals(taipei_la, "16 hours ahead");

        String la_stjohns = TimeFormatter.getTimeDifferenceWithTime(la, stjohns, LA_DaylightSavingsOff);
        assertEquals(la_stjohns, "4.5 hours behind");

        String la_newyork = TimeFormatter.getTimeDifferenceWithTime(la, newyork, LA_DaylightSavingsOff);
        assertEquals(la_newyork, "3 hours behind");

        String la_honolulu = TimeFormatter.getTimeDifferenceWithTime(la, honolulu, LA_DaylightSavingsOff);
        assertEquals(la_honolulu, "2 hours ahead");
    }

    @Test
    public void millisToTimeTest() {
        assertEquals(TimeFormatter.toClockString(0), "00:00 AM");
        assertEquals(TimeFormatter.toMillisOfDay(0, 0), 0);

        long zeroToTaipei = TimeFormatter.toMillisOfTimeZone(0, taipei);
        assertEquals(zeroToTaipei, 57600000);

    }
}