package com.aboutblank.worldscheduler.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.time.TimeService;
import com.aboutblank.worldscheduler.ui.screenstates.ClockPickerScreenState;
import com.aboutblank.worldscheduler.ui.screenstates.State;

import java.util.Set;

public class ClockPickerViewModel extends BaseViewModel {
    private MutableLiveData<ClockPickerScreenState> screenState;
    private TimeService timeService;

    ClockPickerViewModel(WorldApplication application) {
        super(application);
        timeService = application.getTimeService();
    }

    @Override
    void initialize() {
        screenState = new MutableLiveData<>();
        screenState.postValue(new ClockPickerScreenState(State.LOADING));

        getThreadManager().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(timeService == null);
                onRetrieveTimeZones(timeService.getSetTimeZoneNames());
            }
        });
    }

    public static ClockPickerViewModel getClockPickerViewModel(Fragment fragment) {
        return (ClockPickerViewModel) ClockPickerViewModel.getViewModel(fragment, ClockPickerViewModel.class);
    }

    public MutableLiveData<ClockPickerScreenState> getScreenState() {
        return screenState;
    }

    private void onRetrieveTimeZones(Set<String> timeZones) {
        screenState.postValue(new ClockPickerScreenState(State.DONE, timeZones));
    }

    public void saveClock(@NonNull final String timeZoneId) {
        getThreadManager().execute(new Runnable() {
            @Override
            public void run() {
                getDataService().saveClock(timeZoneId);
            }
        });
    }
}
