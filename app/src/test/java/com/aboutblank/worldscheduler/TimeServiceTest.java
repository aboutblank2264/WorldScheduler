package com.aboutblank.worldscheduler;

import com.aboutblank.worldscheduler.backend.time.TimeService;
import com.aboutblank.worldscheduler.backend.time.TimeServiceImpl;

import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class TimeServiceTest {
    private final static int millisInHour = 3600000;
    private final static String testZoneId = "Asia/Taipei";

    private TimeService timeService;

    public TimeServiceTest() {
    }

    @Before
    public void init() {
        timeService = new TimeServiceImpl();
    }

    @Test
    public void fetchData_test() {
        Set<String> timeZones = timeService.getSetTimeZoneNames();
//        System.out.println(timeZones.toString());

        DateTimeZone taipei = timeService.getTimeZone(testZoneId);
        assertNotNull(taipei);

        assertTrue(timeZones.contains(testZoneId));
    }

    @Test
    public void convertDate_test() {

    }
}
