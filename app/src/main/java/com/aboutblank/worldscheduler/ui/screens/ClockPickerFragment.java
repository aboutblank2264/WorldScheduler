package com.aboutblank.worldscheduler.ui.screens;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.ui.Keyboard;
import com.aboutblank.worldscheduler.ui.MainActivity;
import com.aboutblank.worldscheduler.ui.screenstates.ClockPickerScreenState;
import com.aboutblank.worldscheduler.viewmodels.ClockPickerViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ClockPickerFragment extends BaseFragment {
    private final static String LOG = ClockPickerFragment.class.getSimpleName();

    @BindView(R.id.pick_auto_text)
    AutoCompleteTextView autoTextView;
    private ArrayAdapter<String> adapter;

    @BindView(R.id.pick_clear_text)
    Button clearButton;

    private ClockPickerViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel = ClockPickerViewModel.getClockPickerViewModel(this);

        initializeAutoTextView();
        initializeStateObservation();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG, "onStart");
        Keyboard.showKeyboard(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG, "onPause");
        Keyboard.hideKeyboard(this);
    }

    private void initializeAutoTextView() {
        adapter = new ArrayAdapter<>(requireContext(), R.layout.clock_picker_list_item, R.id.picker_text);
        autoTextView.setAdapter(adapter);
        autoTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClockPickerFragment.this.onItemClick(position);
            }
        });
    }

    private void initializeStateObservation() {
        viewModel.getScreenState().observe(this, new Observer<ClockPickerScreenState>() {
            @Override
            public void onChanged(@Nullable ClockPickerScreenState screenState) {
                onStateChanged(screenState);
            }
        });
    }

    public void onStateChanged(ClockPickerScreenState screenState) {
        switch (screenState.getState()) {
            case DONE:
                onTimeZonesReceived(screenState.getTimeZones());
                break;
            case ERROR:
                onError(screenState.getThrowable());
                break;
            case LOADING:
                //Do nothing, the UI should be able to be used from the get go.
                break;
        }
    }

    private void onTimeZonesReceived(List<String> timeZones) {
//        Log.i(LOG, "Received list of timezones: " + timeZones.toString());
        adapter.addAll(timeZones);
        adapter.notifyDataSetChanged();
    }

    private void onError(Throwable throwable) {
        ((MainActivity) requireActivity()).onError(LOG, throwable);
    }

    @OnClick(R.id.pick_clear_text)
    public void onClearClick() {
        autoTextView.getText().clear();
    }

    void onItemClick(int position) {
        String sel = adapter.getItem(position);
        Log.d(LOG, String.format("ItemClicked: position: %d", position));
        Log.d(LOG, "Selected time zone: " + sel);

        viewModel.onItemClicked(sel);
    }

    @Override
    public void showProgressBar() {
        //Do nothing.
    }

    @Override
    public void hideProgressBar() {
        //Do nothing.
    }

    @Override
    public int getLayout() {
        return R.layout.frag_clock_picker;
    }
}
