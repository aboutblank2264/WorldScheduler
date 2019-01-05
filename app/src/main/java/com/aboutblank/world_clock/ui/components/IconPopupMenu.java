package com.aboutblank.world_clock.ui.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.View;

@SuppressLint("RestrictedApi")
public class IconPopupMenu extends PopupMenu {
    private final MenuPopupHelper popupHelper;

    public IconPopupMenu(@NonNull final Context context, @NonNull final View anchor) {
        super(context, anchor);
        popupHelper = new MenuPopupHelper(context, (MenuBuilder) getMenu(), anchor);
        popupHelper.setForceShowIcon(true);
    }

    @Override
    public void show() {
        popupHelper.show();
    }
}
