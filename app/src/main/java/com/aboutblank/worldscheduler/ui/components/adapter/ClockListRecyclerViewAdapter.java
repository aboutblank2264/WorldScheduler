package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.components.SimpleDateClock;
import com.aboutblank.worldscheduler.viewmodels.ClockListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockListRecyclerViewAdapter extends RecyclerView.Adapter<ClockListRecyclerViewAdapter.ClockListHolder> {
    private List<Clock> clocks;
    private ClockListViewModel viewModel;

    public ClockListRecyclerViewAdapter(ClockListViewModel viewModel) {
        this.clocks = new ArrayList<>();
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ClockListRecyclerViewAdapter.ClockListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_clock_list_item, parent, false);
        return new ClockListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClockListHolder holder, int position) {
        holder.setClock(clocks.get(position).getTimeZoneId());
    }

    public void update(List<Clock> newClocks) {
        if (newClocks != null) {
            clocks.clear();
            clocks.addAll(newClocks);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return clocks.size();
    }

    class ClockListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_timezone)
        TextView timeZone;

        @BindView(R.id.item_compare)
        TextView timeZoneCompare;

        @BindView(R.id.item_time)
        SimpleDateClock simpleDateClock;

        ClockListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setClock(String timeZoneId) {

            timeZone.setText(timeZoneId);

            timeZoneCompare.setText(viewModel.getOffSetString(timeZoneId));
            simpleDateClock.setTimeZone(timeZoneId);
        }
    }
}
