package com.aboutblank.worldscheduler.ui.screens;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.WorldApplication;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.MainActivity;
import com.aboutblank.worldscheduler.ui.components.SimpleDateClock;
import com.aboutblank.worldscheduler.ui.components.adapter.ClockListRecyclerViewAdapter;
import com.aboutblank.worldscheduler.ui.screenstates.ScreenState;
import com.aboutblank.worldscheduler.viewmodels.ClockListViewModel;

import java.util.List;

import butterknife.BindView;

public class ClockListFragment extends BaseFragment {
    private final static String LOG = ClockListFragment.class.getSimpleName();

    @BindView(R.id.main_clock)
    SimpleDateClock mainClock;

    @BindView(R.id.main_recycler)
    RecyclerView recyclerView;
    private ClockListRecyclerViewAdapter clockListAdapter;

    private ClockListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                ((WorldApplication) requireContext().getApplicationContext()).getViewModelFactory())
                .get(ClockListViewModel.class);

        mainClock.setTimeZone(viewModel.getLocalClock().getTimeZoneId());

        initializeRecyclerView();
        initializeStateObservation();

        return view;
    }

    private void initializeRecyclerView() {
        clockListAdapter = new ClockListRecyclerViewAdapter(viewModel);
        recyclerView.setAdapter(clockListAdapter);
        recyclerView.setLayoutManager
                (new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void initializeStateObservation() {
        viewModel.getScreenState().observe(this, new Observer<ScreenState>() {
            @Override
            public void onChanged(@Nullable ScreenState screenState) {
                onStateChanged(screenState);
            }
        });
    }

    @Override
    public void onStateChanged(ScreenState screenState) {
        switch (screenState.getState()) {
            case ScreenState.DONE:
                onClocksReceived(screenState.getClocks());
                break;
            case ScreenState.ERROR:
                onError(screenState.getThrowable());
                break;
            case ScreenState.LOADING:
                showProgressBar();
                break;
        }
    }

    private void onClocksReceived(List<Clock> clocks) {
        Log.d(LOG, "Received list of clocks: " + clocks.toString());
        clockListAdapter.update(clocks);
        hideProgressBar();
    }

    private void onError(Throwable throwable) {
        ((MainActivity) requireActivity()).onError(LOG, throwable);
        hideProgressBar();
    }

    @Override
    public void showProgressBar() {
        Log.d(LOG, "Showing progress bar");
    }

    @Override
    public void hideProgressBar() {
        Log.d(LOG, "Hiding progress bar");
    }

    //TODO
    private void onNewClockClicked() {

    }

    //TODO
    private void onClockClicked(int position) {

    }

    @Override
    public int getLayout() {
        return R.layout.frag_clock_list;
    }
}
