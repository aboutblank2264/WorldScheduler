package com.aboutblank.worldscheduler.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aboutblank.worldscheduler.backend.data.Clock;
import com.aboutblank.worldscheduler.ui.screenstates.ScreenState;

import java.util.List;

public class ClockViewModel extends ViewModel {
    private MutableLiveData<List<Clock>> savedClocks;
    private MutableLiveData<ScreenState> screenState;

    public MutableLiveData<List<Clock>> getSavedClocks() {
        return savedClocks;
    }

    public MutableLiveData<ScreenState> getScreenState() {
        return screenState;
    }


}
