package com.aboutblank.world_clock.ui.components.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutblank.world_clock.R;
import com.aboutblank.world_clock.backend.room.Clock;
import com.aboutblank.world_clock.ui.TimeZoneUtils;
import com.aboutblank.world_clock.ui.components.ClockActionOptionButtons;
import com.aboutblank.world_clock.ui.components.SimpleDateClock;
import com.aboutblank.world_clock.ui.components.utils.ClockListDiffCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockListRecyclerViewAdapter extends RecyclerView.Adapter<ClockListRecyclerViewAdapter.ClockListHolder> {
    private final static String LOG = ClockListRecyclerViewAdapter.class.getSimpleName();

    private ClockListAdapterMediator adapterMediator;
    private RecyclerView.RecycledViewPool savedTimeRecyclerPool;
    private List<Clock> clocks;

    public ClockListRecyclerViewAdapter(ClockListAdapterMediator adapterMediator) {
        this.adapterMediator = adapterMediator;
        this.clocks = new ArrayList<>();
    }

    @NonNull
    @Override
    public ClockListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_clock_list_item, parent, false);
        ClockListHolder clockListHolder = new ClockListHolder(view, parent.getContext());

        if (savedTimeRecyclerPool == null) {
            savedTimeRecyclerPool = clockListHolder.savedTimeRecyclerView.getRecycledViewPool();
        } else {
            clockListHolder.savedTimeRecyclerView.setRecycledViewPool(savedTimeRecyclerPool);
        }

        return clockListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ClockListHolder holder, final int position) {
        Clock clock = clocks.get(position);
        holder.setClockInfo(clock);
        holder.setExpanded(adapterMediator.isExpanded(position));

        holder.setOnClickListener(adapterMediator.getOnClickListener());
    }

    @Override
    public int getItemCount() {
        return clocks.size();
    }

    public void update(List<Clock> newClocks) {
        Log.d(LOG, "Update");
        ClockListDiffCallback callback = new ClockListDiffCallback(clocks, newClocks);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        Log.d("DiffOld", clocks.toString());
        Log.d("DiffNew", newClocks.toString());
        clocks.clear();
        clocks.addAll(newClocks);
        result.dispatchUpdatesTo(this);
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

        @BindView(R.id.item_clock_options)
        ClockActionOptionButtons clockActionOptionButtons;

        @BindView(R.id.item_saved_time_rv)
        RecyclerView savedTimeRecyclerView;
        ClockListDetailRecyclerViewAdapter adapter;

        private String timeZoneId;

        ClockListHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            adapter = new ClockListDetailRecyclerViewAdapter(adapterMediator);
            savedTimeRecyclerView.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            savedTimeRecyclerView.setAdapter(adapter);

            setOptionButtonListeners();
        }

        void setClockInfo(@NonNull Clock clock) {
            this.timeZoneId = clock.getTimeZoneId();
            adapter.update(clock);

            timeZone.setText(TimeZoneUtils.format(timeZoneId));
            timeZoneCompare.setText(adapterMediator.getOffSetString(timeZoneId));
            simpleDateClock.setTimeZone(timeZoneId);
        }

        void setOnClickListener(View.OnClickListener listener) {
            layout.setOnClickListener(listener);
        }

        void setOptionButtonListeners() {
            clockActionOptionButtons.setScheduleOnClick(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Log.d(LOG, "Add New " + timeZoneId);
                    adapterMediator.popupNewSaveTime(timeZoneId);
                }
            });

            clockActionOptionButtons.setDeleteOnClick(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Log.d(LOG, "Delete " + timeZoneId);
                    adapterMediator.deleteClock(timeZoneId);
                }
            });
        }

        void setExpanded(boolean activated) {
            setVisible(activated ? View.VISIBLE : View.GONE);
        }

        private void setVisible(int visible) {
            savedTimeRecyclerView.setVisibility(visible);
            clockActionOptionButtons.setVisibility(visible);
        }

        boolean isExpanded() {
            return savedTimeRecyclerView.getVisibility() == View.VISIBLE;
        }
    }
}
