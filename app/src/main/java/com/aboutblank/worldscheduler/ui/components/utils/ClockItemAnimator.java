package com.aboutblank.worldscheduler.ui.components.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class ClockItemAnimator extends RecyclerView.ItemAnimator {

    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull final RecyclerView.State state, @NonNull final RecyclerView.ViewHolder viewHolder, final int changeFlags, @NonNull final List<Object> payloads) {
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
    }

    @Override
    public boolean animateDisappearance(@NonNull final RecyclerView.ViewHolder viewHolder, @NonNull final ItemHolderInfo preLayoutInfo, @Nullable final ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateAppearance(@NonNull final RecyclerView.ViewHolder viewHolder, @Nullable final ItemHolderInfo preLayoutInfo, @NonNull final ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animatePersistence(@NonNull final RecyclerView.ViewHolder viewHolder, @NonNull final ItemHolderInfo preLayoutInfo, @NonNull final ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateChange(@NonNull final RecyclerView.ViewHolder oldHolder, @NonNull final RecyclerView.ViewHolder newHolder, @NonNull final ItemHolderInfo preLayoutInfo, @NonNull final ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public void runPendingAnimations() {

    }

    @Override
    public void endAnimation(final RecyclerView.ViewHolder item) {

    }

    @Override
    public void endAnimations() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder) {
        return true;
    }


}
