package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.components.ClockListDetail;
import com.aboutblank.worldscheduler.ui.components.SimpleDateClock;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockListRecyclerViewAdapter extends RecyclerView.Adapter<ClockListRecyclerViewAdapter.ClockListHolder> {
    private final static String LOG = ClockListRecyclerViewAdapter.class.getSimpleName();

    private List<Clock> clocks;
    private ClockListAdapterMediator adapterMediator;

    private RecyclerView.RecycledViewPool recycledViewPool;

    public ClockListRecyclerViewAdapter(ClockListAdapterMediator adapterMediator) {
        this.clocks = new ArrayList<>();
        this.adapterMediator = adapterMediator;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recycledViewPool = recyclerView.getRecycledViewPool();
    }

    @NonNull
    @Override
    public ClockListRecyclerViewAdapter.ClockListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_clock_list_item, parent, false);
        return new ClockListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClockListHolder holder, final int position) {
        Clock clock = clocks.get(position);
        holder.setClock(clock.getTimeZoneId(), clock.getName(), clock.getSavedTimes());
        holder.setExpanded(adapterMediator.getCurrentExpandedPosition() == position);

        holder.setOnClickListener(adapterMediator.getOnClickListener());
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

        @BindView(R.id.item_layout)
        ConstraintLayout layout;

        @BindView(R.id.item_timezone)
        TextView timeZone;

        @BindView(R.id.item_compare)
        TextView timeZoneCompare;

        @BindView(R.id.item_time)
        SimpleDateClock simpleDateClock;

        @BindView(R.id.item_details)
        ClockListDetail clockListDetail;

        private String timeZoneId;
        private String name;
        private List<Long> savedTimes;

        ClockListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            setDetailListeners();
            clockListDetail.setRecyclerViewPool(recycledViewPool);
        }

        void setClock(@NonNull String timeZoneId, @NonNull String name, List<Long> savedTimes) {
            this.timeZoneId = timeZoneId;
            this.name = name;
            this.savedTimes = savedTimes;

            timeZone.setText(name);

            timeZoneCompare.setText(adapterMediator.getOffSetString(timeZoneId));
            simpleDateClock.setTimeZone(timeZoneId);
        }

        void setOnClickListener(View.OnClickListener listener) {
            layout.setOnClickListener(listener);
        }

        void setDetailListeners() {
            clockListDetail.setOnAddClickedListener(adapterMediator.getOnAddClickedListener());

            clockListDetail.setOnDeleteClickedListener(adapterMediator.getOnDeleteClickedListener());
        }

        void setExpanded(boolean activated) {
            clockListDetail.setVisibility(activated ? View.VISIBLE : View.GONE);
            if(activated) {
                clockListDetail.update(savedTimes);
            }
        }

        boolean isExpanded() {
            return clockListDetail.getVisibility() == View.VISIBLE;
        }
    }
}
