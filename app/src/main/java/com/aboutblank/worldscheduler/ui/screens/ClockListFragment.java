package com.aboutblank.worldscheduler.ui.screens;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.data.Clock;
import com.aboutblank.worldscheduler.ui.screenstates.ScreenState;
import com.aboutblank.worldscheduler.viewmodel.ClockViewModel;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;

public class ClockListFragment extends BaseFragment {
    private final static String LOG = ClockListFragment.class.getSimpleName();

    @BindView(R.id.text_clock_main)
    TextClock main_clock;

    private ClockViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Log.d(LOG, DateTime.now().toString());
        Log.d(LOG, DateTime.now().toLocalTime().toString());
        main_clock.setFormat12Hour(DateTime.now().toLocalTime().toString());

        viewModel = ViewModelProviders.of(this).get(ClockViewModel.class);

        initializeStateObservation();
        initializeClockObservation();
        //TODO initialize RecyclerView
        return view;
    }

    private void initializeStateObservation() {
        viewModel.getScreenState().observe(this, new Observer<ScreenState>() {
            @Override
            public void onChanged(@Nullable ScreenState screenState) {
                onScreenChanged(screenState);
            }
        });
    }

    private void initializeClockObservation() {
        viewModel.getSavedClocks().observe(this, new Observer<List<Clock>>() {
            @Override
            public void onChanged(@Nullable List<Clock> clocks) {
                onClocksRecieved(clocks);
            }
        });
    }

    @Override
    public void onScreenChanged(ScreenState screenState) {
    }

    private void onClocksRecieved(List<Clock> clocks) {

    }

    @Override
    public int getLayout() {
        return R.layout.frag_clock_list;
    }
}
