package com.aboutblank.worldscheduler.ui.components.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import com.aboutblank.worldscheduler.backend.room.Clock;

import java.util.List;

public class ClockWithSavedTime implements Parcelable {
    private Clock clock;
    private List<Long> savedTimes;

    public ClockWithSavedTime(final Clock clock, final List<Long> savedTimes) {
        this.clock = clock;
        this.savedTimes = savedTimes;
    }

    private ClockWithSavedTime(Parcel in) {
    }

    public Clock getClock() {
        return clock;
    }

    public List<Long> getSavedTimes() {
        return savedTimes;
    }

    public static final Creator<ClockWithSavedTime> CREATOR = new Creator<ClockWithSavedTime>() {
        @Override
        public ClockWithSavedTime createFromParcel(Parcel in) {
            return new ClockWithSavedTime(in);
        }

        @Override
        public ClockWithSavedTime[] newArray(int size) {
            return new ClockWithSavedTime[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
    }
}
