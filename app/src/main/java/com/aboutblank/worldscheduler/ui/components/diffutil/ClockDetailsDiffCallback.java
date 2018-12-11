package com.aboutblank.worldscheduler.ui.components.diffutil;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class ClockDetailsDiffCallback extends DiffUtil.Callback {

    private final List<Long> oldList;
    private final List<Long> newList;

    public ClockDetailsDiffCallback(final List<Long> oldList, final List<Long> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
