package com.aboutblank.worldscheduler.backend.time;

import com.aboutblank.worldscheduler.ThreadManager;
import com.aboutblank.worldscheduler.backend.room.LocalDatabase;
import com.aboutblank.worldscheduler.backend.room.TimeZoneDao;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;

import java.util.List;

public class TimeServiceImpl implements TimeService {
    private final static String AHEAD = "hours ahead";
    private final static String BEHIND = "hours behind";

    private TimeZoneDao timeZoneDao;

    private List<String> cityNames;

    public TimeServiceImpl(final LocalDatabase localDatabase, ThreadManager threadManager) {
        this.timeZoneDao = localDatabase.timeZoneDao();
        initialize(threadManager);
    }

    private void initialize(ThreadManager threadManager) {
        threadManager.execute(new Runnable() {
            @Override
            public void run() {
                if (timeZoneDao.count() == 0) {
                    timeZoneDao.insert(TimeFormatter.getListOfTimeZones());
                }
            }
        });
    }

    @Override
    public List<String> getCityNames() {
//        if (cityNames == null) {
//            cityNames = TimeFormatter.formatList(DateTimeZone.getAvailableIDs());
//        }
//        return cityNames;
        return timeZoneDao.getTimeZoneNames();
    }

    @Override
    public String getTimeDifference(String timeZoneId) {
        DateTime currentTime = DateTime.now(DateTimeZone.getDefault());
        DateTime targetTime = DateTime.now(DateTimeZone.forID(timeZoneId));

        Minutes minutes = Minutes.minutesBetween(currentTime.toInstant(), targetTime.toInstant());
        int hoursBetween = minutes.toStandardHours().getHours();
        int remainder = minutes.getMinutes() % 60;

        return String.valueOf(hoursBetween) +
                (remainder >= 30 ? ".5 " : " ") +
                (hoursBetween > 0 ? AHEAD : BEHIND);
    }
}
