package com.aboutblank.worldscheduler.viewmodels;

import android.arch.core.util.Function;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.backend.time.TimeFormatter;
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

    public void onAddDialog() {
        viewModelScreenState.postValue(new ClockListScreenState(State.DIALOG));
    }

    public LiveData<ClockListScreenState> getScreenState() {
        return joinedScreenState;
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<ClockListScreenState> observer) {
        joinedScreenState.observe(owner, observer);
    }

    public void removeObservers(@NonNull LifecycleOwner owner) {
        joinedScreenState.removeObservers(owner);
    }

    public String getLocalTimeZone() {
        return getDataService().getLocalClock().getTimeZoneId();
    }

    public long toMillisOfDay(int hour, int minute) {
        return getDataService().toMillisofDay(hour, minute);
    }

    public String getOffSetString(@NonNull final String timeZoneId) {
        return getDataService().getTimeDifference(timeZoneId);
    }

    public void onFabClick() {
        getFragmentManager().changeToPickerFragment(true);
    }

    public void onDelete(@NonNull final Clock clock) {
        getThreadManager().execute(new Runnable() {
            @Override
            public void run() {
                getDataService().deleteClock(clock);
            }
        });
    }

    public void addTimeAndSave(@NonNull final Clock clock, int hour, int minute) {
        long millisOfDay = toMillisOfDay(hour, minute);
        clock.addSavedTime(millisOfDay);
        addUpdateClock(clock);
    }

    private void addUpdateClock(@NonNull final Clock clock) {
        getThreadManager().execute(new Runnable() {
            @Override
            public void run() {
                getDataService().updateClock(clock);
            }
        });
    }

    public String[] getTimeStrings(final long savedTime, @NonNull final String timeZoneId) {
        String[] res = new String[2];
        res[0] = TimeFormatter.toClockString(savedTime);
        res[1] = TimeFormatter.toClockString(TimeFormatter.toMillisOfTimeZone(savedTime, timeZoneId));
        return res;
    }

    public void deleteSavedTime(@NonNull Clock clock, int position) {
        clock.getSavedTimes().remove(position);
        addUpdateClock(clock);
    }
}
