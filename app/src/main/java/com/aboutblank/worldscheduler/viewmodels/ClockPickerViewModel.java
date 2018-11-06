package com.aboutblank.worldscheduler.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.backend.DataService;
import com.aboutblank.worldscheduler.ui.screenstates.ClockPickerScreenState;
import com.aboutblank.worldscheduler.ui.screenstates.State;

import java.util.List;

public class ClockPickerViewModel extends BaseViewModel {
    private MutableLiveData<ClockPickerScreenState> screenState;
    private DataService dataService;

    private List<String> timeZones;

    ClockPickerViewModel(WorldApplication application) {
        super(application);
        dataService = application.getDataService();
    }

    @Override
    void initialize() {
        screenState = new MutableLiveData<>();
        screenState.postValue(new ClockPickerScreenState(State.LOADING));

        getThreadManager().execute(new Runnable() {
            @Override
            public void run() {
                if (timeZones == null) {
                    timeZones = dataService.getCityNames();
                }
                onRetrieveTimeZones(timeZones);
            }
        });
    }

    public MutableLiveData<ClockPickerScreenState> getScreenState() {
        return screenState;
    }

    private void onRetrieveTimeZones(List<String> timeZones) {
        screenState.postValue(new ClockPickerScreenState(State.DONE, timeZones));
    }

    private void onError(Throwable throwable) {
        screenState.postValue(new ClockPickerScreenState(State.ERROR, throwable));
    }

    private void onMessage(@NonNull String message) {
        screenState.postValue(new ClockPickerScreenState(State.MESSAGE, message));
    }

    public void onItemClicked(@NonNull final String name) {
        getThreadManager().execute(new Runnable() {
            @Override
            public void run() {
                if (getDataService().getClockByName(name) == null) {
                    getDataService().saveClockWithName(name);
                    getFragmentManager().finishCurrentFragment();
                } else {
                    onMessage("This time zone has already be saved!");
                }
            }
        });
    }
}
