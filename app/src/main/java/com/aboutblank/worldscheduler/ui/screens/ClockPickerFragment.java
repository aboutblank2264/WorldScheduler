package com.aboutblank.worldscheduler.ui.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.worldscheduler.ui.screenstates.ScreenState;

public class ClockPickerFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        //TODO initialize ViewModel

        return view;
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void onStateChanged(ScreenState screenState) {
        switch (screenState.getState()) {
            case ScreenState.DONE:
                break;
            case ScreenState.ERROR:
                break;
            case ScreenState.LOADING:
                break;
        }
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
