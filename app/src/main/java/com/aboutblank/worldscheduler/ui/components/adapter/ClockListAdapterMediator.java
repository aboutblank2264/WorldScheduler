package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.aboutblank.worldscheduler.ui.components.IconPopupMenu;

public interface ClockListAdapterMediator {
    boolean isExpanded(int position);

    View.OnClickListener getOnClickListener();

    String getOffSetString(@NonNull String timeZoneId);

    void addAlarm(long savedTime);

    void deleteClock(String timeZoneId);

    void deleteSavedTime(String timeZoneId, long savedTime);

    void popupNewSaveTime(String timeZoneId);

    void popupChangeSavedTime(String timezoneId, long savedTime, String timeString);

    void popupChangeSavedTimeWithConversion(String timezoneId, long savedTime, String timeString);

    IconPopupMenu getPopupMenu(View view);

    String[] getTimeStrings(String timeZoneId, long savedTime);
}
