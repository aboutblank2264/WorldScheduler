package com.aboutblank.worldscheduler.backend.time;

public class TimePair {
    private final long to;
    private final long from;

    public TimePair(final long to, final long from) {
        this.to = to;
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public long getFrom() {
        return from;
    }
}
