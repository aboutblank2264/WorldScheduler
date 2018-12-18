package com.aboutblank.worldscheduler.backend.time;

public class TimeZone {
    private String fullName;
    private String continent;
    private String city;

    private TimeZone() {
    }

    public static TimeZone create(final String fullName, final String continent, final String city) {
        TimeZone timeZone = new TimeZone();
        timeZone.fullName = fullName;
        timeZone.continent = continent;
        timeZone.city = city;

        return timeZone;
    }

    public String getContinent() {
        return continent;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
