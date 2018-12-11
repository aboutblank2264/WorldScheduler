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
        LiveData<ClockListScreenState> dataSourceScreenState =
                Transformations.map(getDataService().getAllClocksLive(), new Function<List<Clock>, ClockListScreenState>() {
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
                break;
            case DELETE_CLOCK:
                onDelete(event.getTimezoneId());
                break;
            case ADD_SAVED_TIME:
                addSavedTimeToClock(event.getTimezoneId(), event.getHour(), event.getMinute());
                break;
            case DELETE_SAVED_TIME:
                deleteSavedTime(event.getTimezoneId(), event.getSavedTimePosition());
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
                addAlarm(event.getTimestring(), event.getTag());
                break;
        }
    }

    private void postValue(ClockListScreenState screenState) {
        viewModelScreenState.postValue(screenState);
    }

    private void postError(Throwable throwable) {
        postValue(ClockListScreenState.error(throwable));
    }

    private void postLocalTimeZone() {
        postValue(ClockListScreenState.localTimeZone(getDataService().getLocalClock().getTimeZoneId()));
    }

    private void postMillisOfDay(int hour, int minute) {
        postValue(ClockListScreenState.millisOfDay(getDataService().toMillisOfDay(hour, minute)));
    }

    public String getOffSetString(@NonNull final String timeZoneId) {
        return getDataService().getTimeDifference(timeZoneId);
    }

    private void onFabClick() {
        getFragmentManager().changeToPickerFragment(true);
    }

    private void onDelete(final String timeZoneId) {
        if (timeZoneId == null) {
            postError(new IllegalArgumentException("TimeZoneId cannot be null"));
        } else {
            getThreadManager().execute(new Runnable() {
                @Override
                public void run() {
                    getDataService().deleteClock(timeZoneId);
                    postValue(ClockListScreenState.deleteClock());
                }
            });
        }
    }

    @SuppressLint("DefaultLocale")
    private void addSavedTimeToClock(final String timeZoneId, final int hour, final int minute) {
        debug(LOG, String.format("TimeZoneId: %s, hour: %d, minute: %d", timeZoneId, hour, minute));

        if (timeZoneId == null) {
            postError(new IllegalArgumentException("TimeZoneId cannot be null"));
        } else {
            getThreadManager().execute(new Runnable() {
                @Override
                public void run() {
                    getDataService().addSavedTimeToClock(timeZoneId, hour, minute);
                    postValue(ClockListScreenState.addNewSavedTime());
                }
            });
        }
    }

    public String[] getFormattedTimeStrings(final String timeZoneId, final long savedTime) {
        return getDataService().getFormattedTimeStrings(timeZoneId, savedTime);
    }

    public long toMillisFromTimeString(final String timeString) {
        return getDataService().getMillisFromTimeString(timeString);
    }

    private void deleteSavedTime(final String timeZoneId, final int position) {
        if (timeZoneId == null) {
            postError(new IllegalArgumentException("TimeZoneId cannot be null"));
        } else {
            getThreadManager().execute(new Runnable() {
                @Override
                public void run() {
                    getDataService().deleteSavedTimeFromClock(timeZoneId, position);
                    postValue(ClockListScreenState.deleteSavedTime(position));
                }
            });
        }
    }

    private void addAlarm(final String timeString, final String tag) {
        long millis = getDataService().getMillisFromTimeString(timeString);

        getFragmentManager().addAlarm(tag, getDataService().getHourOfDay(millis),
                getDataService().getMinuteOfHour(millis));
    }

    public void showDialog(DialogFragment dialogFragment, String tag) {
        getFragmentManager().showDialog(dialogFragment, tag);
    }
}
