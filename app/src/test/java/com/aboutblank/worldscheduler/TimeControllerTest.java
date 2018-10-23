package com.aboutblank.worldscheduler;

import com.aboutblank.worldscheduler.backend.time.TimeController;
import com.aboutblank.worldscheduler.backend.time.TimeControllerImpl;

import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class TimeControllerTest {
    private final static int millisInHour = 3600000;
    private final static String testZoneId = "Asia/Taipei";

    private TimeController timeController;

    public TimeControllerTest() {
    }

    @Before
    public void init() {
        timeController = new TimeControllerImpl();
    }

    @Test
    public void fetchData_test() {
        Set<String> timeZones = timeController.getSetTimeZoneNames();
//        System.out.println(timeZones.toString());

        DateTimeZone taipei = timeController.getTimeZone(testZoneId);
        assertNotNull(taipei);

        assertTrue(timeZones.contains(testZoneId));
    }

    @Test
    public void convertDate_test() {

    }
}
