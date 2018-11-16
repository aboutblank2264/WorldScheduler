package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.aboutblank.worldscheduler.ui.components.ClockListDetail;

public interface ClockListAdapterMediator {
    int getCurrentExpandedPosition();

    View.OnClickListener getOnClickListener();

    String getOffSetString(@NonNull String timeZoneId);

    ClockListDetail.OnAddClickedListener getOnAddClickedListener();

    ClockListDetail.OnDeleteClickedListener getOnDeleteClickedListener();
}
