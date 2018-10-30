package com.aboutblank.worldscheduler.ui.screens;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.MainActivity;
import com.aboutblank.worldscheduler.ui.components.SimpleDateClock;
import com.aboutblank.worldscheduler.ui.components.adapter.ClockListRecyclerViewAdapter;
import com.aboutblank.worldscheduler.ui.screenstates.ClockListScreenState;
import com.aboutblank.worldscheduler.viewmodels.ClockListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ClockListFragment extends BaseFragment {
    private final static String LOG = ClockListFragment.class.getSimpleName();

    @BindView(R.id.list_clock)
    SimpleDateClock mainClock;

    @BindView(R.id.list_recycler)
    RecyclerView recyclerView;
    private ClockListRecyclerViewAdapter clockListAdapter;

    @BindView(R.id.list_new_fab)
    FloatingActionButton fab;

    private ClockListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel = ClockListViewModel.getClockListViewModel(this);

        mainClock.setTimeZone(viewModel.getLocalTimeZone());

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
        viewModel.getScreenState().observe(this, new Observer<ClockListScreenState>() {
            @Override
            public void onChanged(@Nullable ClockListScreenState screenState) {
                onStateChanged(screenState);
            }
        });
    }

    public void onStateChanged(ClockListScreenState screenState) {
        Log.d(LOG, "State Received: " + screenState.toString());
        switch (screenState.getState()) {
            case DONE:
                onClocksReceived(screenState.getClocks());
                break;
            case ERROR:
                onError(screenState.getThrowable());
                break;
            case LOADING:
                showProgressBar();
                break;
        }
    }

    private void onClocksReceived(List<Clock> clocks) {
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

    @OnClick(R.id.list_new_fab)
    public void onNewClockClicked() {
        viewModel.onFabClick();
    }

    //TODO
    private void onClockClicked(int position) {
    }

    @Override
    public int getLayout() {
        return R.layout.frag_clock_list;
    }
}
