package com.aboutblank.worldscheduler.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.aboutblank.worldscheduler.WorldApplication;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private WorldApplication application;

    public ViewModelFactory(WorldApplication application) {
        this.application = application;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == ClockListViewModel.class) {
            return (T) new ClockListViewModel(application);
        }
        if(modelClass == ClockPickerViewModel.class) {
            return (T) new ClockPickerViewModel(application);
        }

        return super.create(modelClass);
    }
}
