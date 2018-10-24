package com.aboutblank.worldscheduler.ui.screens;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.screenstates.ScreenState;
import com.aboutblank.worldscheduler.viewmodels.ClockListViewModel;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;

public class ClockListFragment extends BaseFragment {
    private final static String LOG = ClockListFragment.class.getSimpleName();

    @BindView(R.id.text_clock_main)
    TextClock main_clock;

    private ClockListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        main_clock.setFormat12Hour(DateTime.now().toLocalTime().toString());

        viewModel = ViewModelProviders.of(this,
                ((WorldApplication) requireContext().getApplicationContext()).getViewModelFactory())
                .get(ClockListViewModel.class);

        initializeStateObservation();
        //TODO initialize RecyclerView
        return view;
    }

    private void initializeStateObservation() {
        viewModel.getScreenState().observe(this, new Observer<ScreenState>() {
            @Override
            public void onChanged(@Nullable ScreenState screenState) {
                onStateChanged(screenState);
            }
        });
    }

    private void onClocksReceived(List<Clock> clocks) {

    }

    //TODO
    private void onNewClockClicked() {

    }

    //TODO
    private void onClockClicked(int position) {

    }

    @Override
    public void onStateChanged(ScreenState screenState) {
        switch (screenState.getState()) {
            case ScreenState.DONE:
                break;
            case ScreenState.ERROR:
                break;
            case ScreenState.LOADING:
                break;
        }
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public int getLayout() {
        return R.layout.frag_clock_list;
    }
}
