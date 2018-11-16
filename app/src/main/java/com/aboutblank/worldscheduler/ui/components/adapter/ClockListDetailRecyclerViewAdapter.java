package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.time.TimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class ClockListDetailRecyclerViewAdapter extends RecyclerView.Adapter<ClockListDetailRecyclerViewAdapter.ClockListDetailViewHolder> {
    private List<Long> savedTimes = new ArrayList<>();

    @NonNull
    @Override
    public ClockListDetailViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_clock_detail_item, parent, false);
        return new ClockListDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClockListDetailViewHolder holder, final int position) {
        long time = savedTimes.get(position);
        int[] clockTime = TimeFormatter.toClockTime(time);
    }

    public void update(List<Long> times) {
        savedTimes.clear();
//        savedTimes.addAll(times);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ClockListDetailViewHolder extends RecyclerView.ViewHolder {

        public ClockListDetailViewHolder(final View itemView) {
            super(itemView);
        }
    }
}
