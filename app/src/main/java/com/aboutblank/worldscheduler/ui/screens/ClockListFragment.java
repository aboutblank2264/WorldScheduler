package com.aboutblank.worldscheduler.ui.screens;

import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.TransitionManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TimePicker;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.MainActivity;
import com.aboutblank.worldscheduler.ui.components.SimpleDateClock;
import com.aboutblank.worldscheduler.ui.components.adapter.ClockListAdapterMediator;
import com.aboutblank.worldscheduler.ui.components.adapter.ClockListRecyclerViewAdapter;
import com.aboutblank.worldscheduler.ui.screenstates.ClockListScreenState;
import com.aboutblank.worldscheduler.viewmodels.ClockListViewModel;
import com.aboutblank.worldscheduler.viewmodels.ViewModelFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ClockListFragment extends BaseFragment implements ClockListAdapterMediator {
    private final static String LOG = ClockListFragment.class.getSimpleName();

    @BindView(R.id.list_clock)
    SimpleDateClock mainClock;

    @BindView(R.id.list_recycler)
    RecyclerView recyclerView;
    private ClockListRecyclerViewAdapter clockListAdapter;

    @BindView(R.id.list_new_fab)
    FloatingActionButton fab;

    private ClockListViewModel viewModel;
    private int currentExpandedPosition = -1;
    private List<Clock> clocks;

    private Observer<ClockListScreenState> observer = new Observer<ClockListScreenState>() {
        @Override
        public void onChanged(@Nullable final ClockListScreenState screenState) {
            onStateChanged(screenState);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel = ViewModelFactory.getClockListViewModel(this);

        mainClock.setTimeZone(viewModel.getLocalTimeZone());

        initializeRecyclerView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.observe(this, observer);
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.removeObservers(this);
    }

    private void initializeRecyclerView() {
        clockListAdapter = new ClockListRecyclerViewAdapter(this);
        recyclerView.setAdapter(clockListAdapter);
        recyclerView.setLayoutManager
                (new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
    }

    public void onStateChanged(final ClockListScreenState screenState) {
        Log.d(LOG, "State Received: " + screenState.toString());
        Log.d(LOG, String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
        switch (screenState.getState()) {
            case LOADING:
                showProgressBar();
                break;
            case DONE:
                onClocksReceived(screenState.getClocks());
                break;
            case ERROR:
                onError(screenState.getThrowable());
                break;
        }
    }

    private void onClocksReceived(List<Clock> clocks) {
        this.clocks = clocks;
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

    @Override
    public int getLayout() {
        return R.layout.frag_clock_list;
    }

    /*------------------------------------------------------------------*/
    /*------------------- ClockListAdapterMediator ---------------------*/
    /*------------------------------------------------------------------*/

    @Override
    public int getCurrentExpandedPosition() {
        return currentExpandedPosition;
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                currentExpandedPosition =
                        (currentExpandedPosition == recyclerView.getChildAdapterPosition(v)) ?
                                -1 : recyclerView.getChildAdapterPosition(v);
                Log.d(LOG, "OnClick " + currentExpandedPosition);
                TransitionManager.beginDelayedTransition(recyclerView);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        };
    }

    @Override
    public String getOffSetString(@NonNull final String timeZoneId) {
        return viewModel.getOffSetString(timeZoneId);
    }

    private TimePickerDialog.OnTimeSetListener getOnTimeSetListener(final int position) {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(final TimePicker view, final int hour, final int minute) {
                viewModel.addTimeAndSave(clocks.get(position), hour, minute);
                //TODO add listener to respond to this instead of calling explicitly.
            }
        };
    }

    @Override
    public void onDelete(final int position) {
        viewModel.onDelete(clocks.get(position));
        //TODO add a listener so that UI can respond smoothly.
    }

    @Override
    public void addNew(final int position) {
        new TimePickerDialog(requireContext(), getOnTimeSetListener(position), 0, 0, false)
                .show();
    }

    @Override
    public PopupMenu getPopupMenu(View view) {
        return new PopupMenu(requireContext(), view);
    }

    @Override
    public String[] getTimeStrings(final long savedTime) {
        return viewModel.getTimeStrings(savedTime, clocks.get(currentExpandedPosition).getTimeZoneId());
    }
}
