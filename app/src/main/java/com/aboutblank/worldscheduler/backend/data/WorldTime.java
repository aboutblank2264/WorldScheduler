package com.aboutblank.worldscheduler.backend.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class WorldTime {
    private String id;
    private @Month int month;
//    private


    @IntDef({JANUARY, FEBRUARY, MARCH, MAY, APRIL, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Month {

    }

    public final static int JANUARY = 1;
    public final static int FEBRUARY = 2;
    public final static int MARCH = 3;
    public final static int APRIL = 4;
    public final static int MAY = 5;
    public final static int JUNE = 6;
    public final static int JULY = 7;
    public final static int AUGUST = 8;
    public final static int SEPTEMBER = 9;
    public final static int OCTOBER = 10;
    public final static int NOVEMBER = 11;
    public final static int DECEMBER = 12;
}
