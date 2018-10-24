package com.aboutblank.worldscheduler.ui;

import com.aboutblank.worldscheduler.ui.screenstates.ScreenState;

public interface Screen {

    void onStateChanged(ScreenState screenState);

    void showProgressBar();

    void hideProgressBar();
}
