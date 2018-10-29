package com.aboutblank.worldscheduler;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.backend.DataService;
import com.aboutblank.worldscheduler.backend.DataServiceImpl;
import com.aboutblank.worldscheduler.backend.room.LocalDatabase;
import com.aboutblank.worldscheduler.time.TimeService;
import com.aboutblank.worldscheduler.time.TimeServiceImpl;
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

        localDatabase = Room.databaseBuilder(this, LocalDatabase.class, LocalDatabase.class.getName())
                .fallbackToDestructiveMigration()
                .build();

        dataService = new DataServiceImpl(getLocalDatabase());

        threadManager = new ThreadManager();

        viewModelFactory = new ViewModelFactory(this);

        timeService = new TimeServiceImpl();
    }

    public DataService getDataService() {
        if (dataService == null) {
            throwException(DataService.class.getSimpleName());
        }
        return dataService;
    }

    public LocalDatabase getLocalDatabase() {
        if (localDatabase == null) {
            throwException(LocalDatabase.class.getSimpleName());
        }
        return localDatabase;
    }

    public ThreadManager getThreadManager() {
        if (threadManager == null) {
            throwException(ThreadManager.class.getSimpleName());
        }
        return threadManager;
    }

    public ViewModelFactory getViewModelFactory() {
        if (viewModelFactory == null) {
            throwException(ViewModelFactory.class.getSimpleName());
        }
        return viewModelFactory;
    }

    public TimeService getTimeService() {
        if (timeService == null) {
            throwException(TimeService.class.getSimpleName());
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
