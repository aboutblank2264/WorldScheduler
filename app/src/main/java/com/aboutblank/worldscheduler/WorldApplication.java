package com.aboutblank.worldscheduler;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.aboutblank.worldscheduler.backend.DataService;
import com.aboutblank.worldscheduler.backend.DataServiceImpl;
import com.aboutblank.worldscheduler.backend.room.LocalDatabase;
import com.aboutblank.worldscheduler.viewmodels.ViewModelFactory;

import net.danlew.android.joda.JodaTimeAndroid;

public class WorldApplication extends Application {
    private DataService dataService;
    private LocalDatabase localDatabase;
    private ThreadManager threadManager;
    private ViewModelFactory viewModelFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }

    public DataService getDataService() {
        if (dataService == null) {
            dataService = new DataServiceImpl(getLocalDatabase());
        }
        return dataService;
    }

    public LocalDatabase getLocalDatabase() {
        if (localDatabase == null) {
            localDatabase = Room.databaseBuilder(this, LocalDatabase.class, LocalDatabase.class.getName())
                    .fallbackToDestructiveMigration()
                    .build();
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
        if(viewModelFactory == null) {
            viewModelFactory = new ViewModelFactory(this);
        }
        return viewModelFactory;
    }
}
