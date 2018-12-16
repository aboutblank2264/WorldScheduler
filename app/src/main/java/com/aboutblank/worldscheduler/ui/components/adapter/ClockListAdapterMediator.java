package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.aboutblank.worldscheduler.ui.components.IconPopupMenu;

import java.util.List;

public interface ClockListAdapterMediator {
    int getClockCount();

    int getCurrentExpandedPosition();

    View.OnClickListener getOnClickListener();

    String getOffSetString(@NonNull String timeZoneId);

    void addAlarm(String timeString);

    void deleteClock(int position);

    void deleteSavedTime(long savedTime);

    void popupNewSaveTime(int positionOfClock);

    void popupChangeSaveTime(String timeZoneId, String timeString, long oldSavedTime);

    IconPopupMenu getPopupMenu(View view);

    String[] getTimeStrings(long savedTime);

    List<Long> getSavedTimes(String timeZoneId);
}
