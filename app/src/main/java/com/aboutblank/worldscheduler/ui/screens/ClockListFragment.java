package com.aboutblank.worldscheduler.ui.screens;

import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.MainActivity;
import com.aboutblank.worldscheduler.ui.components.IconPopupMenu;
import com.aboutblank.worldscheduler.ui.components.SimpleDateClock;
import com.aboutblank.worldscheduler.ui.components.TagDialog;
import com.aboutblank.worldscheduler.ui.components.adapter.ClockListAdapterMediator;
import com.aboutblank.worldscheduler.ui.components.adapter.ClockListRecyclerViewAdapter;
import com.aboutblank.worldscheduler.ui.screenstates.ClockListScreenState;
import com.aboutblank.worldscheduler.viewmodels.ClockListViewModel;
import com.aboutblank.worldscheduler.viewmodels.ViewModelFactory;
import com.aboutblank.worldscheduler.viewmodels.events.ClockListEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ClockListFragment extends BaseFragment implements ClockListAdapterMediator {
    private final static String LOG = ClockListFragment.class.getSimpleName();

    private final static String EXPAND_POSITION = "expandPosition";

    @BindView(R.id.list_clock)
    SimpleDateClock mainClock;

    @BindView(R.id.list_recycler)
    RecyclerView recyclerView;
    private ClockListRecyclerViewAdapter clockListAdapter;
//    private ClockListViewAdapter clockListAdapter;

    @BindView(R.id.list_new_fab)
    FloatingActionButton fab;

    private ClockListViewModel viewModel;
    private int currentExpandedPosition = -1;
    private List<Clock> clocks = new ArrayList<>();
    private Map<String, List<Long>> savedTimeMap = new HashMap<>();

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

        initializeRecyclerView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.observe(this, observer);
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.removeObservers(this);
    }

    private void initializeRecyclerView() {
//        clockListAdapter = new ClockListViewAdapter();
        clockListAdapter = new ClockListRecyclerViewAdapter(this);
        recyclerView.setAdapter(clockListAdapter);
        recyclerView.setLayoutManager
                (new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
    }

    public void onStateChanged(final ClockListScreenState screenState) {
        Log.d(LOG, "ClockListState Received: " + screenState.toString());
        switch (screenState.getState()) {
            case LOADING:
                showProgressBar();
                break;
            case CLOCKS:
                onClocksReceived(screenState.getClocks());
                break;
            case ERROR:
                onError(screenState.getThrowable());
                break;
            case LOCAL_TIMEZONE:
                mainClock.setTimeZone(screenState.getTimeZoneId());
                break;
            case MILLIS_OF_DAY:
                break;
            case OFFSET_STRING:
                break;
            case FORMAT_TIME_STRINGS:
                break;
            case ADD_NEW_SAVED_TIME:
            case DELETE_SAVED_TIME:
            case GET_SAVED_TIMES:
                onUpdateSavedTimes(screenState.getTimeZoneId(), screenState.getSavedTimes());
                break;
            case DELETE_CLOCK:
                onDeleteClock(screenState.getTimeZoneId());
                break;
        }
    }

    private void onClocksReceived(List<Clock> clocks) {
        Log.d(LOG, "onClockReceived: " + clocks.toString());
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

    private void onUpdateSavedTimes(@NonNull String timeZoneId, List<Long> savedTimes) {
        Log.d(LOG, String.format("TimeZone: %s, SavedTimes: %s", timeZoneId, savedTimes.toString()));

        savedTimeMap.put(timeZoneId, savedTimes);

        if (clocks.get(currentExpandedPosition).getTimeZoneId().equals(timeZoneId)) {
            clockListAdapter.notifyItemChanged(currentExpandedPosition);
        }
    }

    private void onDeleteClock(String timeZoneId) {
        expandClockView(-1, true);
        savedTimeMap.remove(timeZoneId);
    }

    @OnClick(R.id.list_new_fab)
    public void onNewClockClicked() {
        postEvent(ClockListEvent.fabClick());
    }

    @Override
    public int getLayout() {
        return R.layout.frag_clock_list;
    }

    /*------------------------------------------------------------------*/
    /*------------------- ClockListAdapterMediator ---------------------*/
    /*------------------------------------------------------------------*/

    @Override
    public int getClockCount() {
        return clocks.size();
    }

    @Override
    public int getCurrentExpandedPosition() {
        return currentExpandedPosition;
    }

    private void expandClockView(int position, boolean overridePrevious) {
        Log.d(LOG, "OnClick " + currentExpandedPosition);
        currentExpandedPosition = (currentExpandedPosition == position && overridePrevious) ? -1 : position;
//        TransitionManager.beginDelayedTransition(recyclerView);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ClockListFragment.this.expandClockView(recyclerView.getChildAdapterPosition(v), true);
            }
        };
    }

    @Override
    public String getOffSetString(@NonNull final String timeZoneId) {
        return viewModel.getOffSetString(timeZoneId);
    }

    private TimePickerDialog.OnTimeSetListener getOnNewSavedTimeListener(final int position) {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(final TimePicker view, final int hour, final int minute) {
                postEvent(ClockListEvent.addSavedTime(clocks.get(position).getTimeZoneId(), hour, minute));
            }
        };
    }

    private TimePickerDialog.OnTimeSetListener getOnChangeSavedTimeListener(final int position, final long oldSavedTime) {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(final TimePicker view, final int hour, final int minute) {
                postEvent(ClockListEvent.changeSavedTime(clocks.get(position).getTimeZoneId(), hour, minute, oldSavedTime));
            }
        };
    }

    @Override
    public void deleteClock(final int position) {
        postEvent(ClockListEvent.deleteClock(clocks.get(position).getTimeZoneId()));
    }

    @Override
    public void deleteSavedTime(final long savedTime) {
        postEvent(ClockListEvent.deleteSavedTime(clocks.get(currentExpandedPosition).getTimeZoneId(), savedTime));
    }

    @Override
    public void addAlarm(final String timeString) {
        TagDialog tagDialog = TagDialog.newInstance(new TagDialog.TagDialogListener() {
            @Override
            public void onPositiveClick(final TagDialog dialog, final String message) {
                postEvent(ClockListEvent.addAlarm(timeString, message));
                dialog.dismiss();
            }

            @Override
            public void onNegativeClick(final TagDialog dialog) {
                dialog.dismiss();
            }
        });

        viewModel.showDialog(tagDialog, "Add Alarm");
    }

    @Override
    public void popupNewSaveTime(final int positionOfClock) {
        new TimePickerDialog(requireContext(), getOnNewSavedTimeListener(positionOfClock), 0, 0, false)
                .show();
    }

    @Override
    public void popupChangeSaveTime(final String timeZoneId, final String timeString, final long oldSavedTime) {
        int[] timeFields = viewModel.convertTimeStringToNumeric(timeString);
        new TimePickerDialog(requireContext(), getOnChangeSavedTimeListener(currentExpandedPosition, oldSavedTime),
                timeFields[0], timeFields[1], false);
    }


    @Override
    public IconPopupMenu getPopupMenu(View view) {
        Context wrapper = new ContextThemeWrapper(requireContext(), R.style.AppTheme_PopupMenu);
        return new IconPopupMenu(wrapper, view);
    }

    @Override
    public String[] getTimeStrings(final long savedTime) {
        Log.d(LOG, Arrays.toString(viewModel.getFormattedTimeStrings(clocks.get(currentExpandedPosition).getTimeZoneId(), savedTime)));

        return viewModel.getFormattedTimeStrings(clocks.get(currentExpandedPosition).getTimeZoneId(), savedTime);
    }

    @Override
    public List<Long> getSavedTimes(final String timeZoneId) {
        if (savedTimeMap.get(timeZoneId) != null) {
            return savedTimeMap.get(timeZoneId);
        }
        postEvent(ClockListEvent.getSavedTimes(timeZoneId));

        return Collections.emptyList();
    }

    private void postEvent(ClockListEvent event) {
        if (viewModel != null) {
            viewModel.consumeEvent(event);
        }
    }
}
