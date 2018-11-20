package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.time.TimeFormatter;
import com.aboutblank.worldscheduler.backend.time.TimePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockListDetailRecyclerViewAdapter extends RecyclerView.Adapter<ClockListDetailRecyclerViewAdapter.ClockListDetailViewHolder> {
    private List<TimePair> savedTimes = new ArrayList<>();
    private ClockListAdapterMediator adapterMediator;

    @NonNull
    @Override
    public ClockListDetailViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_clock_detail_item, parent, false);
        return new ClockListDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClockListDetailViewHolder holder, final int position) {
        TimePair pair = savedTimes.get(position);
        String fromTime = TimeFormatter.toClockTime(pair.getFrom());
        String toTime = TimeFormatter.toClockTime(pair.getTo());

        holder.setTimes(fromTime, toTime);
    }

    public void setAdapterMediator(final ClockListAdapterMediator adapterMediator) {
        this.adapterMediator = adapterMediator;
    }

    public void update(List<TimePair> times) {
        savedTimes.clear();
        savedTimes.addAll(times);
    }

    @Override
    public int getItemCount() {
        return savedTimes.size();
    }

    class ClockListDetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_from_clock)
        TextView fromClock;

        @BindView(R.id.detail_to_clock)
        TextView toClock;

        ClockListDetailViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setTimes(String fromTime, String toTime) {
            fromClock.setText(fromTime);
            toClock.setText(toTime);
        }
    }
}
