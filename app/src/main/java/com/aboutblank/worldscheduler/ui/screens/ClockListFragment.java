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
import android.transition.TransitionManager;
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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ClockListFragment extends BaseFragment implements ClockListAdapterMediator {
    private final static String LOG = ClockListFragment.class.getSimpleName();

    private final static String EXPEND_POSITION = "expended_position";

    @BindView(R.id.list_clock)
    SimpleDateClock mainClock;

    @BindView(R.id.list_recycler)
    RecyclerView recyclerView;
    private ClockListRecyclerViewAdapter clockListAdapter;

    @BindView(R.id.list_new_fab)
    FloatingActionButton fab;

    private ClockListViewModel viewModel;
    private int currentExpandedPosition = -1;
    private List<Clock> clocks = new ArrayList<>();

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
        Log.d(LOG, "onSaveInstanceState, expandedPosition : " + currentExpandedPosition);
        outState.putInt(EXPEND_POSITION, currentExpandedPosition);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            setCurrentExpandedPosition(savedInstanceState.getInt(EXPEND_POSITION, -1));
            Log.d(LOG, "onViewStateRestored, expandedPosition : " + currentExpandedPosition);
        }
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
                break;
        }
    }

    private void setCurrentExpandedPosition(int i) {
        currentExpandedPosition = i;
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
        Log.d(LOG, String.format("onUpdateSavedTimes\nTimeZone: %s, SavedTimes: %s", timeZoneId, savedTimes.toString()));

        if (clocks.get(currentExpandedPosition).getTimeZoneId().equals(timeZoneId)) {
            clockListAdapter.notifyItemChanged(currentExpandedPosition);
        }
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
    public boolean isExpanded(int position) {
        Log.d(LOG, String.format("isExpanded: %d position %d", currentExpandedPosition, position));
        return currentExpandedPosition != -1 && currentExpandedPosition == position;
    }

    private void expandCollapseView(int position) {
        Log.d(LOG, String.format("OnClick %d to %d", currentExpandedPosition, position));
        setCurrentExpandedPosition(currentExpandedPosition == position ? -1 : position);
        TransitionManager.beginDelayedTransition(recyclerView);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                expandCollapseView(recyclerView.getChildAdapterPosition(v));
            }
        };
    }

    @Override
    public String getOffSetString(@NonNull final String timeZoneId) {
        return viewModel.getOffSetString(timeZoneId);
    }

    private TimePickerDialog.OnTimeSetListener getOnNewSavedTimeListener(final String timeZoneId) {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(final TimePicker view, final int hour, final int minute) {
                Log.d(LOG, String.format("Add saved time: %s, %d:%d", timeZoneId, hour, minute));
                postEvent(ClockListEvent.addSavedTime(timeZoneId, hour, minute));
            }
        };
    }

    private TimePickerDialog.OnTimeSetListener getOnChangeSavedTimeListener(final String timeZoneId, final long millis) {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(final TimePicker view, final int hour, final int minute) {
                Log.d(LOG, String.format("Change saved time: %s, %d:%d", timeZoneId, hour, minute));
                postEvent(ClockListEvent.changeSavedTime(timeZoneId, hour, minute, millis));
            }
        };
    }

    private TimePickerDialog.OnTimeSetListener getOnChangeSavedTimeListenerWithConversion(final String timeZoneId, final long millis) {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(final TimePicker view, final int hour, final int minute) {
                Log.d(LOG, String.format("Change saved time: %s, %d:%d, convert", timeZoneId, hour, minute));
                postEvent(ClockListEvent.changeSavedTime(timeZoneId, hour, minute, millis, true));
            }
        };
    }

    @Override
    public void deleteClock(final String timeZoneId) {
        Log.d(LOG, "Deleting clock: " + timeZoneId);
        setCurrentExpandedPosition(-1);
        postEvent(ClockListEvent.deleteClock(timeZoneId));
    }

    @Override
    public void deleteSavedTime(@NonNull final String timeZoneId, final long savedTime) {
        Log.d(LOG, String.format("Deleting saved time: %s, %d", timeZoneId, savedTime));
        postEvent(ClockListEvent.deleteSavedTime(timeZoneId, savedTime));
    }

    @Override
    public void addAlarm(final long savedTime) {
        TagDialog tagDialog = TagDialog.newInstance(new TagDialog.TagDialogListener() {
            @Override
            public void onPositiveClick(final TagDialog dialog, final String message) {
                Log.d(LOG, String.format("Add alarm time: %d, tag: %s", savedTime, message));
                postEvent(ClockListEvent.addAlarm(savedTime, message));
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
    public void popupNewSaveTime(final String timeZoneId) {
        new TimePickerDialog(requireContext(), getOnNewSavedTimeListener(timeZoneId),
                0, 0, false).show();
    }

    @Override
    public void popupChangeSavedTime(final String timezoneId, final long savedTime, final String timeString) {
        int[] hourMinute = viewModel.getHourAndMinuteFromTimeSring(timeString);
        new TimePickerDialog(requireContext(), getOnChangeSavedTimeListener(timezoneId, savedTime),
                hourMinute[0], hourMinute[1], false).show();
    }

    @Override
    public void popupChangeSavedTimeWithConversion(final String timezoneId, final long savedTime, final String timeString) {
        int[] hourMinute = viewModel.getHourAndMinuteFromTimeSring(timeString);
        new TimePickerDialog(requireContext(), getOnChangeSavedTimeListenerWithConversion(timezoneId, savedTime),
                hourMinute[0], hourMinute[1], false).show();
    }

    @Override
    public IconPopupMenu getPopupMenu(View view) {
        Context wrapper = new ContextThemeWrapper(requireContext(), R.style.AppTheme_PopupMenu);
        return new IconPopupMenu(wrapper, view);
    }

    @Override
    public String[] getTimeStrings(final String timeZoneId, final long savedTime) {
        Log.d(LOG, Arrays.toString(viewModel.getFormattedTimeStrings(timeZoneId, savedTime)));

        return viewModel.getFormattedTimeStrings(timeZoneId, savedTime);
    }

    private void postEvent(ClockListEvent event) {
        if (viewModel != null) {
            viewModel.consumeEvent(event);
        }
    }
}
