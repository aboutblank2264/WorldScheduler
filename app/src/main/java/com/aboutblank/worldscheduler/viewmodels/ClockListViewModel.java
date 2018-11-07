package com.aboutblank.worldscheduler.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.screens.ClockPickerFragment;
import com.aboutblank.worldscheduler.ui.screenstates.ClockListScreenState;
import com.aboutblank.worldscheduler.ui.screenstates.State;

import java.util.List;

public class ClockListViewModel extends BaseViewModel {
    private final static String LOG = ClockListViewModel.class.getSimpleName();
    private MutableLiveData<ClockListScreenState> screenState;

    private Observer<List<Clock>> observer;

    ClockListViewModel(WorldApplication application) {
        super(application);
    }

    @Override
    void initialize() {
        debug(LOG, "initializing");

        screenState = new MutableLiveData<>();
        screenState.setValue(new ClockListScreenState(State.LOADING));

        getDataService().getAllClocksLive().observeForever(getObserver());
    }

    private Observer<List<Clock>> getObserver() {
        if(observer == null) {
            observer = new Observer<List<Clock>>() {
                @Override
                public void onChanged(@Nullable List<Clock> clocks) {
                    onRetrieveClocks(clocks);
                }
            };
        }

        return observer;
    }

    private void onRetrieveClocks(List<Clock> clocks) {
        screenState.setValue(new ClockListScreenState(State.DONE, clocks));
    }

    private void onError(Throwable throwable) {
        screenState.postValue(new ClockListScreenState(State.ERROR, throwable));
    }

    public MutableLiveData<ClockListScreenState> getScreenState() {
        return screenState;
    }

    public String getLocalTimeZone() {
        return getDataService().getLocalClock().getTimeZoneId();
    }

    public String getOffSetString(@NonNull final String timeZoneId) {
        return getDataService().getTimeDifference(timeZoneId);
    }

    public void onFabClick() {
        getFragmentManager().changeFragmentView(new ClockPickerFragment(), true);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        debug(LOG, "onCleared");
        getDataService().getAllClocksLive().removeObserver(getObserver());
    }
}
