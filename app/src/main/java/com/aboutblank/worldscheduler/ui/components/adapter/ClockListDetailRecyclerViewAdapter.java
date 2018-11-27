package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutblank.worldscheduler.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockListDetailRecyclerViewAdapter extends RecyclerView.Adapter<ClockListDetailRecyclerViewAdapter.ClockListDetailViewHolder> {
    private List<Long> savedTimes = new ArrayList<>();
    private ClockListAdapterMediator adapterMediator;

    ClockListDetailRecyclerViewAdapter(final ClockListAdapterMediator adapterMediator) {
        this.adapterMediator = adapterMediator;
    }

    @NonNull
    @Override
    public ClockListDetailViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_clock_detail_item, parent, false);
        return new ClockListDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClockListDetailViewHolder holder, final int position) {
        String[] times = adapterMediator.getTimeStrings(savedTimes.get(position));
        holder.setTimes(times[0], times[1]);
    }

    void update(List<Long> times) {
        savedTimes.clear();
        savedTimes.addAll(times);
    }

    @Override
    public int getItemCount() {
        return savedTimes.size();
    }

    class ClockListDetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_layout)
        ViewGroup layout;

        @BindView(R.id.detail_from_clock)
        TextView fromClock;

        @BindView(R.id.detail_to_clock)
        TextView toClock;

        ClockListDetailViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(final View v, final MotionEvent event) {
                    Log.d("OnTouchListener", event.toString());
                    return false;
                }
            });
        }

        void setTimes(String fromTime, String toTime) {
            fromClock.setText(fromTime);
            toClock.setText(toTime);
        }
    }
}
