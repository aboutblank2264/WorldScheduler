package com.aboutblank.worldscheduler.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aboutblank.worldscheduler.backend.room.Clock;
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

    public void initialize() {
        screenState.postValue(new ScreenState(ScreenState.LOADING));

        //TODO Get some data from repo

        screenState.postValue(new ScreenState(ScreenState.DONE));
    }


}
