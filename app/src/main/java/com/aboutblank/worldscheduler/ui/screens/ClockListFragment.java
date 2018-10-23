package com.aboutblank.worldscheduler.ui.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;

import com.aboutblank.worldscheduler.R;

import org.joda.time.DateTime;

import butterknife.BindView;

public class ClockListFragment extends BaseFragment {

    @BindView(R.id.text_clock_main)
    TextClock main_clock;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        main_clock.setFormat12Hour(DateTime.now().toString());
        return view;
    }

    @Override public int getLayout() {
        return R.layout.frag_clock_list;
    }
}
