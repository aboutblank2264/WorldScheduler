package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.PopupMenu;

public interface ClockListAdapterMediator {
    int getCurrentExpandedPosition();

    View.OnClickListener getOnClickListener();

    String getOffSetString(@NonNull String timeZoneId);

    void onDelete(int position);

    void addNew(int position);

    PopupMenu getPopupMenu(View view);

    String[] getTimeStrings(long savedTime);
}
