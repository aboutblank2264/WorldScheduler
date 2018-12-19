package com.aboutblank.worldscheduler.viewmodels;

import android.annotation.SuppressLint;
import android.arch.core.util.Function;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.screenstates.ClockListScreenState;
import com.aboutblank.worldscheduler.viewmodels.events.ClockListEvent;

import java.util.List;

public class ClockListViewModel extends BaseViewModel {
    private final static String LOG = ClockListViewModel.class.getSimpleName();

    private LiveData<ClockListScreenState> dataSourceScreenState;
    private MutableLiveData<ClockListScreenState> viewModelScreenState;
    private MediatorLiveData<ClockListScreenState> joinedScreenState;

    ClockListViewModel(WorldApplication application) {
        super(application);
    }

    @Override
    void initialize() {
        debug(LOG, "initializing");

        viewModelScreenState = new MutableLiveData<>();
        viewModelScreenState.setValue(ClockListScreenState.loading());

        //Transformer to get the LiveData list of clocks from data service.
        dataSourceScreenState = Transformations.map(getDataService().getAllClocksLive(),
                new Function<List<Clock>, ClockListScreenState>() {
                    @Override
                    public ClockListScreenState apply(final List<Clock> input) {
                        return ClockListScreenState.clocks(input);
                    }
                });

        Observer<ClockListScreenState> observer = new Observer<ClockListScreenState>() {
            @Override
            public void onChanged(@Nullable final ClockListScreenState clockListScreenState) {
                joinedScreenState.setValue(clockListScreenState);
            }
        };

        //Joining both Data Service LiveData and events returned from user actions.
        joinedScreenState = new MediatorLiveData<>();
        joinedScreenState.addSource(viewModelScreenState, observer);
        joinedScreenState.addSource(dataSourceScreenState, observer);

        debug(LOG, "initialize done");
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<ClockListScreenState> observer) {
        joinedScreenState.observe(owner, observer);
    }

    public void removeObservers(@NonNull LifecycleOwner owner) {
        joinedScreenState.removeObservers(owner);
    }

    public void consumeEvent(ClockListEvent event) {
        debug(LOG, event.toString());
        switch (event.getEvent()) {
            case LOAD_CLOCKS:
                postClocks();
                break;
            case DELETE_CLOCK:
                postDeleteClock(event.getTimezoneId());
                break;
            case GET_SAVED_TIMES:
                postSavedTimes(event.getTimezoneId());
                break;
            case ADD_SAVED_TIME:
                postAddSavedTime(event.getTimezoneId(), event.getHour(), event.getMinute());
                break;
            case DELETE_SAVED_TIME:
                postDeleteSavedTime(event.getTimezoneId(), event.getSavedTime());
                break;
            case CHANGE_SAVED_TIME:
                postChangeSavedTime(event.getTimezoneId(), event.getHour(), event.getMinute(), event.getSavedTime());
                break;
            case GET_MILLIS_OF_DAY:
                postMillisOfDay(event.getHour(), event.getMinute());
                break;
            case GET_LOCAL_TIMEZONE:
                postLocalTimeZone();
                break;
//            case GET_OFFSET_STRING:
//                postOffSetString(event.getTimezoneId());
//                break;
//            case GET_FORMATTED_TIME_STRINGS:
//                postFormattedTimeStrings(event.getTimezoneId(), event.getSavedTimePosition());
//                break;
            case FAB_CLICK:
                onFabClick();
                break;
            case ADD_ALARM:
                addAlarm(event.getSavedTime(), event.getTag());
                break;
        }
    }

    //Call this method to post ScreenState to UI
    private void postValue(ClockListScreenState screenState) {
        viewModelScreenState.postValue(screenState);
    }

    private void postError(Throwable throwable) {
        postValue(ClockListScreenState.error(throwable));
    }

    private void postClocks() {
        postValue(dataSourceScreenState.getValue());
    }

    private void postLocalTimeZone() {
        postValue(ClockListScreenState.localTimeZone(getDataService().getLocalTimeZone()));
    }

    private void postMillisOfDay(int hour, int minute) {
        postValue(ClockListScreenState.millisOfDay(getDataService().toMillisOfDay(hour, minute)));
    }

    private void postDeleteClock(final String timeZoneId) {
        if (timeZoneId == null) {
            postError(new IllegalArgumentException("TimeZoneId cannot be null"));
        } else {
            getThreadManager().execute(new Runnable() {
                @Override
                public void run() {
                    getDataService().deleteClock(timeZoneId);
                }
            });
        }
    }

    @SuppressLint("DefaultLocale")
    private void postAddSavedTime(final String timeZoneId, final int hour, final int minute) {
        debug(LOG, String.format("TimeZoneId: %s, hour: %d, minute: %d", timeZoneId, hour, minute));

        if (timeZoneId == null) {
            postError(new IllegalArgumentException("TimeZoneId cannot be null"));
        } else {
            getThreadManager().execute(new Runnable() {
                @Override
                public void run() {
                    getDataService().addSavedTime(timeZoneId, hour, minute);
                    postValue(ClockListScreenState.addNewSavedTime(timeZoneId, getDataService().getSavedTimes(timeZoneId)));
                }
            });
        }
    }

    private void postSavedTimes(final String timeZoneId) {
        checkTimeZoneRunRunnable(timeZoneId, new Runnable() {
            @Override
            public void run() {
                postValue(ClockListScreenState.getSavedTimes(timeZoneId, getDataService().getSavedTimes(timeZoneId)));
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void postChangeSavedTime(final String timeZoneId, final int hour, final int minute, final long oldSavedTime) {
        debug(LOG, String.format("TimeZoneId: %s, hour: %d, minute: %d, oldTime: %d", timeZoneId, hour, minute, oldSavedTime));
        checkTimeZoneRunRunnable(timeZoneId, new Runnable() {
            @Override
            public void run() {
                getDataService().changeSavedTime(timeZoneId, hour, minute, oldSavedTime);
            }
        });
    }

    private void postDeleteSavedTime(final String timeZoneId, final long savedTime) {
        checkTimeZoneRunRunnable(timeZoneId, new Runnable() {
            @Override
            public void run() {
                getDataService().deleteSavedTime(timeZoneId, savedTime);
                postValue(ClockListScreenState.deleteSavedTime(timeZoneId, getDataService().getSavedTimes(timeZoneId)));
            }
        });
    }

    public String getOffSetString(@NonNull final String timeZoneId) {
        return getDataService().getTimeDifference(timeZoneId);
    }

    public String[] getFormattedTimeStrings(final String timeZoneId, final long savedTime) {
        return getDataService().getFormattedTimeStrings(timeZoneId, savedTime);
    }

    public void showDialog(DialogFragment dialogFragment, String tag) {
        getFragmentManager().showDialog(dialogFragment, tag);
    }

    private void onFabClick() {
        getFragmentManager().changeToPickerFragment(true);
    }

    private void addAlarm(final long millis, final String tag) {
        int hour = getDataService().getHourOfDay(millis);
        int min = getDataService().getMinuteOfHour(millis);

        getFragmentManager().addAlarm(tag, hour, min);
    }

    private void checkTimeZoneRunRunnable(final String timeZoneId, @NonNull final Runnable runnable) {
        if (timeZoneId == null) {
            postError(new IllegalArgumentException("TimeZoneId cannot be null"));
        } else {
            getThreadManager().execute(runnable);
        }
    }
}
