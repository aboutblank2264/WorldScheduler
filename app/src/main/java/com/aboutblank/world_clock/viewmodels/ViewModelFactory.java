package com.aboutblank.world_clock.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.aboutblank.world_clock.WorldApplication;
import com.aboutblank.world_clock.ui.screens.ClockListFragment;
import com.aboutblank.world_clock.ui.screens.ClockPickerFragment;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private WorldApplication application;

    public ViewModelFactory(WorldApplication application) {
        this.application = application;
    }

    private static <T extends ViewModel>  T getViewModel(Fragment fragment, Class<T> clazz) {
        return ViewModelProviders.of(fragment,
                ((WorldApplication) fragment.requireActivity().getApplicationContext()).getViewModelFactory())
                .get(clazz);
    }

    public static ClockListViewModel getClockListViewModel(ClockListFragment fragment) {
        return getViewModel(fragment, ClockListViewModel.class);
    }

    public static ClockPickerViewModel getClockPickerViewModel(ClockPickerFragment fragment) {
        return getViewModel(fragment, ClockPickerViewModel.class);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == ClockListViewModel.class) {
            return (T) new ClockListViewModel(application);
        }
        if (modelClass == ClockPickerViewModel.class) {
            return (T) new ClockPickerViewModel(application);
        }

        return super.create(modelClass);
    }
}
