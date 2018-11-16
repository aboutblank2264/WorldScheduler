package com.aboutblank.worldscheduler.ui.screens;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.aboutblank.worldscheduler.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockDetailAddDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private final static String LOG = ClockDetailAddDialog.class.getSimpleName();

    @BindView(R.id.detail_from)
    Button fromButton;

    @BindView(R.id.detail_to)
    Button toButton;

    @BindView(R.id.detail_time)
    TextView clock;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this, requireActivity());
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // hour, minute
        return new TimePickerDialog(requireContext(), this, 0, 0, false);
    }

    @Override
    public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
        Log.d(LOG, hourOfDay + " " + minute);
        //TODO save the time given.
        clock.setText(String.format("%d:%d", hourOfDay, minute));
    }
}
