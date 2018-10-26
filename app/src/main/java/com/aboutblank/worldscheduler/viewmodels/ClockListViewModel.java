package com.aboutblank.worldscheduler.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.ThreadManager;
import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.backend.DataService;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.screenstates.ScreenState;

import java.util.List;

public class ClockListViewModel extends ViewModel {
    private MutableLiveData<ScreenState> screenState = new MutableLiveData<>();

    private ThreadManager threadManager;
    private DataService dataService;

    public MutableLiveData<ScreenState> getScreenState() {
        return screenState;
    }

    public ClockListViewModel(WorldApplication application) {
        this.threadManager = application.getThreadManager();
        this.dataService = application.getDataService();

        initialize();
    }

    private void initialize() {
        screenState = new MutableLiveData<>();
        screenState.setValue(new ScreenState(ScreenState.LOADING));

        threadManager.execute(new Runnable() {
            @Override
            public void run() {
                onRetrieveClocks(dataService.getAllClocks());
            }
        });
    }

    private void onRetrieveClocks(List<Clock> clocks) {
        screenState.postValue(new ScreenState(ScreenState.DONE, clocks));
    }

    public void saveClock(@NonNull final String timeZoneId) {
        threadManager.execute(new Runnable() {
            @Override
            public void run() {
                dataService.saveClock(timeZoneId);
            }
        });
    }

    public Clock getLocalClock() {
        return dataService.getLocalClock();
    }
}
