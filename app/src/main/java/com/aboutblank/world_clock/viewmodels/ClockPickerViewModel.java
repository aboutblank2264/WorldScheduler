package com.aboutblank.world_clock.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.aboutblank.world_clock.WorldApplication;
import com.aboutblank.world_clock.backend.time.TimeZone;
import com.aboutblank.world_clock.ui.screenstates.ClockPickerScreenState;
import com.aboutblank.world_clock.ui.screenstates.ClockPickerScreenState.ClockPickerState;

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
        screenState.postValue(new ClockPickerScreenState(ClockPickerState.DONE, timeZones));
    }

    private void onError(Throwable throwable) {
        screenState.postValue(new ClockPickerScreenState(ClockPickerState.ERROR, throwable));
    }

    private void onMessage(@NonNull String message) {
        screenState.postValue(new ClockPickerScreenState(ClockPickerState.MESSAGE, message));
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
