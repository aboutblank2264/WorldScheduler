package com.aboutblank.worldscheduler;

import android.app.Application;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.backend.DataService;
import com.aboutblank.worldscheduler.backend.DataServiceImpl;
import com.aboutblank.worldscheduler.backend.room.LocalDatabase;
import com.aboutblank.worldscheduler.backend.time.TimeService;
import com.aboutblank.worldscheduler.backend.time.TimeServiceImpl;
import com.aboutblank.worldscheduler.ui.MainActivity;
import com.aboutblank.worldscheduler.ui.MainFragmentManager;
import com.aboutblank.worldscheduler.viewmodels.ViewModelFactory;

import net.danlew.android.joda.JodaTimeAndroid;

public class WorldApplication extends Application {
    private LocalDatabase localDatabase;
    private DataService dataService;
    private ThreadManager threadManager;
    private TimeService timeService;
    private ViewModelFactory viewModelFactory;
    private MainFragmentManager fragmentManager;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

    }

    public DataService getDataService() {
        if (dataService == null) {
            dataService = new DataServiceImpl(getLocalDatabase(), getTimeService());
        }
        return dataService;
    }

    public LocalDatabase getLocalDatabase() {
        if (localDatabase == null) {
            localDatabase = LocalDatabase.getDatabase(this);
        }
        return localDatabase;
    }

    public ThreadManager getThreadManager() {
        if (threadManager == null) {
            threadManager = new ThreadManager();
        }
        return threadManager;
    }

    public ViewModelFactory getViewModelFactory() {
        if (viewModelFactory == null) {
            viewModelFactory = new ViewModelFactory(this);
        }
        return viewModelFactory;
    }

    public TimeService getTimeService() {
        if (timeService == null) {
            timeService = new TimeServiceImpl();
        }
        return timeService;
    }

    public MainFragmentManager setMainActivity(@NonNull MainActivity mainActivity) {
        fragmentManager = new MainFragmentManager(mainActivity);

        return fragmentManager;
    }

    public MainFragmentManager getFragmentManager() {
        if (fragmentManager == null) {
            throwException(MainFragmentManager.class.getSimpleName());
        }
        return fragmentManager;
    }

    private void throwException(String className) {
        throw new IllegalStateException(className + " not properly set.");
    }
}
