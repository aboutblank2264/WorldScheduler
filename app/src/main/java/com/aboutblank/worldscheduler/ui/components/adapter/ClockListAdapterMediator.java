package com.aboutblank.worldscheduler.ui.components.adapter;

import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.PopupMenu;

public interface ClockListAdapterMediator {
    int getCurrentExpandedPosition();

    View.OnClickListener getOnClickListener();

    String getOffSetString(@NonNull String timeZoneId);

    TimePickerDialog.OnTimeSetListener getOnTimeSetListener();

    PopupMenu getPopupMenu(View view);
}
