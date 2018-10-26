package com.aboutblank.worldscheduler.backend;

import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.backend.room.LocalDatabase;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

public class DataServiceImpl implements DataService {
    private final static long MILLIS_TO_HOUR = 3600000;
    private final static long MILLIS_TO_HALF = MILLIS_TO_HOUR / 2;

    private final static String AHEAD = "hours ahead";
    private final static String BEHIND = "hours behind";

    private LocalDatabase localDatabase;

    public DataServiceImpl(LocalDatabase localDatabase) {
        this.localDatabase = localDatabase;
    }

    @Override
    public Clock getClockById(String timeZoneId) {
        return localDatabase.getClockById(timeZoneId);
    }

    @Override
    public List<Clock> getAllClocks() {
        return localDatabase.getAllClocks();
    }

    @Override
    public void saveClock(String timeZoneId) {
        localDatabase.saveClock(timeZoneId);
    }

    @Override
    public void deleteClock(String timeZoneId) {
        localDatabase.deleteClock(timeZoneId);
    }

    @Override
    public Clock getLocalClock() {
        return new Clock(DateTimeZone.getDefault().getID());
    }

    @Override
    public long getOffset(String timeZoneId) {
        DateTimeZone target = DateTimeZone.forID(timeZoneId);
        return DateTimeZone.getDefault().getOffset(DateTime.now(target));
    }

    @Override
    public String getOffsetString(String timeZoneId) {
        long offset = getOffset(timeZoneId);

        long hours = offset / MILLIS_TO_HOUR;
        long remainder = offset % MILLIS_TO_HOUR;

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(hours);

        if (remainder / MILLIS_TO_HALF == 1) {
            strBuilder.append(".")
                    .append(5);
        }
        strBuilder.append(offset > 0 ? AHEAD : BEHIND);

        return strBuilder.toString();
    }
}
