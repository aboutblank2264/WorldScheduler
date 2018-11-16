package com.aboutblank.worldscheduler.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.aboutblank.worldscheduler.ThreadManager;
import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.backend.DataService;
import com.aboutblank.worldscheduler.ui.MainFragmentManager;

abstract class BaseViewModel extends ViewModel {
    private ThreadManager threadManager;
    private DataService dataService;
    private MainFragmentManager fragmentManager;
    private ResourcesProvider provider;

    BaseViewModel(WorldApplication worldApplication) {
        this.threadManager = worldApplication.getThreadManager();
        this.dataService = worldApplication.getDataService();
        this.fragmentManager = worldApplication.getFragmentManager();
        this.provider = worldApplication.getResourcesProvider();
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

    ResourcesProvider getProvider() {
        return provider;
    }

    String getString(@StringRes final int strId) {
        return provider.getString(strId);
    }

    void debug(@NonNull String tag, @NonNull String message) {
        provider.debug(tag, message);
    }

    void error(@NonNull String tag, @NonNull String message) {
        provider.error(tag, message);
    }
}
