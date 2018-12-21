package com.aboutblank.worldscheduler.backend.time;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeZone timeZone = (TimeZone) o;
        return Objects.equals(fullName, timeZone.fullName) &&
                Objects.equals(continent, timeZone.continent) &&
                Objects.equals(city, timeZone.city);
    }
}
