package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.components.ClockListDetail;
import com.aboutblank.worldscheduler.ui.components.SimpleDateClock;
import com.aboutblank.worldscheduler.viewmodels.ClockListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockListRecyclerViewAdapter extends RecyclerView.Adapter<ClockListRecyclerViewAdapter.ClockListHolder> {
    private final static String LOG = ClockListRecyclerViewAdapter.class.getSimpleName();

    private List<Clock> clocks;
    private ClockListViewModel viewModel;
    private RecyclerView recyclerView;

    private int previousExpanded = -1;

    public ClockListRecyclerViewAdapter(ClockListViewModel viewModel) {
        this.clocks = new ArrayList<>();
        this.viewModel = viewModel;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
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
        holder.setClock(clock.getTimeZoneId(), clock.getName());
        holder.setExpanded(previousExpanded == position);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                previousExpanded = holder.isExpanded() ? -1 : holder.getAdapterPosition();
                TransitionManager.beginDelayedTransition(recyclerView);
                notifyDataSetChanged();
            }
        });
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

        ClockListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            setDetailListeners();
        }

        void setClock(@NonNull String timeZoneId, @NonNull String name) {
            this.timeZoneId = timeZoneId;
            this.name = name;

            timeZone.setText(name);

            timeZoneCompare.setText(viewModel.getOffSetString(timeZoneId));
            simpleDateClock.setTimeZone(timeZoneId);

            //TODO set saved times.
        }

        void setOnClickListener(View.OnClickListener listener) {
            layout.setOnClickListener(listener);
        }

        void setDetailListeners() {
            clockListDetail.setOnAddClickedListener(new ClockListDetail.OnAddClickedListener() {
                @Override
                public void onAdd() {
                    Log.d(LOG, "OnAdd Clicked");
                }
            });

            clockListDetail.setOnDeleteClickedListener(new ClockListDetail.OnDeleteClickedListener() {
                @Override
                public void onDelete() {
                    Log.d(LOG, "OnDelete Clicked");
                    viewModel.onDelete(timeZoneId);
                }
            });
        }

        void setExpanded(boolean activated) {
            clockListDetail.setVisibility(activated ? View.VISIBLE : View.GONE);
            if(activated) {
                //TODO do things.
            }
        }

        boolean isExpanded() {
            return clockListDetail.getVisibility() == View.VISIBLE;
        }
    }
}
