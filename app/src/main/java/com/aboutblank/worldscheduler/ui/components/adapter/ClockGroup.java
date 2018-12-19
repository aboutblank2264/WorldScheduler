package com.aboutblank.worldscheduler.ui.components.adapter;

import com.aboutblank.worldscheduler.backend.room.Clock;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ClockGroup extends ExpandableGroup {

    public ClockGroup(final Clock clock, final List<Long> times) {
        super(clock.getTimeZoneId(), times);
    }
}
