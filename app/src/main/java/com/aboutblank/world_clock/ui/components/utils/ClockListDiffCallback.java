package com.aboutblank.world_clock.ui.components.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.aboutblank.world_clock.backend.room.Clock;

import java.util.List;

public class ClockListDiffCallback extends DiffUtil.Callback {

    private final List<Clock> oldList;
    private final List<Clock> newList;

    public ClockListDiffCallback(final List<Clock> oldList, final List<Clock> newList) {
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

    @Nullable
    @Override
    public Object getChangePayload(final int oldItemPosition, final int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
