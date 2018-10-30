package com.aboutblank.worldscheduler.backend;

public class TimeZone {
    private final String id;
    private final String name;

    public TimeZone(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TimeZone { " +
                "id = '" + id + '\'' +
                ", name = '" + name + '\'' +
                '}';
    }
}
