package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.aboutblank.worldscheduler.ui.components.IconPopupMenu;

public interface ClockListAdapterMediator {
    int getClockCount();

    int getCurrentExpandedPosition();

    View.OnClickListener getOnClickListener();

    String getOffSetString(@NonNull String timeZoneId);

    void addAlarm(String timeString);

    void addNewSavedTime(int position);

    void deleteClock(int position);

    void deleteSavedTime(int savedTimePosition);

    IconPopupMenu getPopupMenu(View view);

    String[] getTimeStrings(long savedTime);
}
