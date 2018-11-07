package com.aboutblank.worldscheduler.viewmodels;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
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
    private MutableLiveData<ClockListScreenState> viewModelScreenState;

    private MediatorLiveData<ClockListScreenState> joinedScreenState;

    ClockListViewModel(WorldApplication application) {
        super(application);
    }

    @Override
    void initialize() {
        debug(LOG, "initializing");

        viewModelScreenState = new MutableLiveData<>();
        viewModelScreenState.setValue(new ClockListScreenState(State.LOADING));

        LiveData<ClockListScreenState> dataSourceScreenState = Transformations.map(getDataService().getAllClocksLive(), new Function<List<Clock>, ClockListScreenState>() {
            @Override
            public ClockListScreenState apply(final List<Clock> input) {
                return new ClockListScreenState(State.DONE, input);
            }
        });

        Observer<ClockListScreenState> observer = new Observer<ClockListScreenState>() {
            @Override
            public void onChanged(@Nullable final ClockListScreenState clockListScreenState) {
                joinedScreenState.setValue(clockListScreenState);
            }
        };

        joinedScreenState = new MediatorLiveData<>();
        joinedScreenState.addSource(viewModelScreenState, observer);
        joinedScreenState.addSource(dataSourceScreenState, observer);
    }

    private void onError(Throwable throwable) {
        viewModelScreenState.postValue(new ClockListScreenState(State.ERROR, throwable));
    }

    public LiveData<ClockListScreenState> getScreenState() {
        return joinedScreenState;
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
}
