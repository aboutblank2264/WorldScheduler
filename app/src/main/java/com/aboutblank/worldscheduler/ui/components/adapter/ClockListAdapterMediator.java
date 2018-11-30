package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.PopupMenu;

import com.aboutblank.worldscheduler.backend.room.Clock;

public interface ClockListAdapterMediator {
    Clock getClockAt(int position);

    int getClockCount();

    int getCurrentExpandedPosition();

    View.OnClickListener getOnClickListener();

    String getOffSetString(@NonNull String timeZoneId);

    void onDelete(int position);

    void addNew(int position);

    PopupMenu getPopupMenu(View view);

    String[] getTimeStrings(long savedTime);
}
