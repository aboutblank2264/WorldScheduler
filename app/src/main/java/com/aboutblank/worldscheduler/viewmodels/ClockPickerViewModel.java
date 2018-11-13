package com.aboutblank.worldscheduler.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.backend.room.TimeZone;
import com.aboutblank.worldscheduler.ui.screenstates.ClockPickerScreenState;
import com.aboutblank.worldscheduler.ui.screenstates.State;

import java.util.List;

public class ClockPickerViewModel extends BaseViewModel {
    private final static String LOG = ClockPickerViewModel.class.getSimpleName();

    private MutableLiveData<ClockPickerScreenState> screenState;

    private List<TimeZone> timeZones;

    ClockPickerViewModel(WorldApplication application) {
        super(application);
    }

    @Override
    void initialize() {
        screenState = new MutableLiveData<>();
        screenState.postValue(new ClockPickerScreenState(State.LOADING));

        if (timeZones == null) {
            getThreadManager().execute(new Runnable() {
                @Override
                public void run() {
                    timeZones = getDataService().getTimeZones();
                    onRetrieveTimeZones(timeZones);
                }
            });
        } else {
            onRetrieveTimeZones(timeZones);
        }
    }

    public MutableLiveData<ClockPickerScreenState> getScreenState() {
        return screenState;
    }

    private void onRetrieveTimeZones(List<TimeZone> timeZones) {
        screenState.postValue(new ClockPickerScreenState(State.DONE, timeZones));
    }

    private void onError(Throwable throwable) {
        screenState.postValue(new ClockPickerScreenState(State.ERROR, throwable));
    }

    private void onMessage(@NonNull String message) {
        screenState.postValue(new ClockPickerScreenState(State.MESSAGE, message));
    }

    public void onItemClicked(@NonNull final String timeZoneId) {
        getThreadManager().execute(new Runnable() {
            @Override
            public void run() {
                if (getDataService().getClockById(timeZoneId) == null) {
                    getDataService().saveClockWithId(timeZoneId);
                    getFragmentManager().finishCurrentFragment();
                } else {
                    onMessage("This time zone has already be saved!");
                }
            }
        });
    }
}
