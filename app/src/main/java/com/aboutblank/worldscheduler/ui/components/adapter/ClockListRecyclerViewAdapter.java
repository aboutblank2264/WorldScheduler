package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
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

public class ClockListRecyclerViewAdapter extends ListAdapter<Clock, ClockListRecyclerViewAdapter.ClockListHolder> {
    private List<Clock> clocks = new ArrayList<>();
    private ClockListViewModel viewModel;

    public ClockListRecyclerViewAdapter(ClockListViewModel viewModel) {
        super(DIFF_CALLBACK);
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
        holder.setClock(getItem(position));
    }

    public void update(List<Clock> newClocks) {
        if (newClocks != null) {
            clocks.addAll(newClocks);
            submitList(clocks);
        }
    }

    class ClockListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_timezone)
        TextView timeZone;

        @BindView(R.id.item_compare)
        TextView timeZoneCompare;

        @BindView(R.id.item_time)
        SimpleDateClock simpleDateClock;

        private Clock clock;

        ClockListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setClock(Clock clock) {
            this.clock = clock;

            timeZone.setText(clock.getName());

            timeZoneCompare.setText(viewModel.getOffSetString(clock.getTimeZoneId()));
            simpleDateClock.setTimeZone(clock.getTimeZoneId());
        }
    }

    private final static DiffUtil.ItemCallback<Clock> DIFF_CALLBACK = new DiffUtil.ItemCallback<Clock>() {
        @Override
        public boolean areItemsTheSame(final Clock oldItem, final Clock newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(final Clock oldItem, final Clock newItem) {
            return oldItem.equals(newItem);
        }
    };
}
