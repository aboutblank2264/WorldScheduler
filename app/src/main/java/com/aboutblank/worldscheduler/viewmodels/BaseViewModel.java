package com.aboutblank.worldscheduler.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.aboutblank.worldscheduler.ThreadManager;
import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.backend.DataService;
import com.aboutblank.worldscheduler.ui.MainFragmentManager;

abstract class BaseViewModel extends ViewModel {
    private ThreadManager threadManager;
    private DataService dataService;
    private MainFragmentManager fragmentManager;

    BaseViewModel(WorldApplication worldApplication) {
        this.threadManager = worldApplication.getThreadManager();
        this.dataService = worldApplication.getDataService();
        this.fragmentManager = worldApplication.getFragmentManager();
        initialize();
    }

    abstract void initialize();

    ThreadManager getThreadManager() {
        return threadManager;
    }

    DataService getDataService() {
        return dataService;
    }

    MainFragmentManager getFragmentManager() {
        return fragmentManager;
    }
}
