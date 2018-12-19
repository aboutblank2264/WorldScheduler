package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.components.IconPopupMenu;
import com.aboutblank.worldscheduler.ui.components.utils.ClockDetailsDiffCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockListDetailRecyclerViewAdapter extends RecyclerView.Adapter<ClockListDetailRecyclerViewAdapter.ClockListDetailViewHolder> {
    private static final String LOG = ClockListDetailRecyclerViewAdapter.class.getSimpleName();

    private String timeZoneId;
    private List<Long> savedTimes;
    private ClockListAdapterMediator adapterMediator;

    ClockListDetailRecyclerViewAdapter(final ClockListAdapterMediator adapterMediator) {
        this.adapterMediator = adapterMediator;
        this.savedTimes = new ArrayList<>();
    }

    @NonNull
    @Override
    public ClockListDetailViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_clock_detail_item, parent, false);
        return new ClockListDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClockListDetailViewHolder holder, final int position) {
        String[] times = adapterMediator.getTimeStrings(timeZoneId, savedTimes.get(position));
        holder.setMillis(savedTimes.get(position));
        holder.setTimeStrings(times[0], times[1]);
    }

    void update(Clock clock) {
        timeZoneId = clock.getTimeZoneId();

        ClockDetailsDiffCallback callback = new ClockDetailsDiffCallback(savedTimes, clock.getSavedTimes());
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        savedTimes.clear();
        savedTimes.addAll(clock.getSavedTimes());

        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return savedTimes.size();
    }

    class ClockListDetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_local_clock)
        TextView localClock;

        @BindView(R.id.detail_other_clock)
        TextView otherClock;

        @BindView(R.id.detail_options)
        Button optionsButton;

        private long millis;

//        private View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                adapterMediator.popupChangeSaveTime(timeZoneId, String.valueOf(((TextView) v).getText()), millis);
//            }
//        };

        ClockListDetailViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            setMenuButton();
        }

        void setMillis(long time) {
            this.millis = time;
        }

        void setTimeStrings(String localTime, String otherTime) {
            localClock.setText(localTime);
            otherClock.setText(otherTime);

//            localClock.setOnClickListener(listener);
//            otherClock.setOnClickListener(listener);
        }

        void setMenuButton() {
            optionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    IconPopupMenu popupMenu = adapterMediator.getPopupMenu(optionsButton);
                    popupMenu.inflate(R.menu.clock_list_side_menu);
                    popupMenu.setOnMenuItemClickListener(new IconPopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.add_new:
                                    Log.d(LOG, "Add Alarm");
                                    adapterMediator.addAlarm(millis);
                                    break;
                                case R.id.delete:
                                    Log.d(LOG, "Delete");
                                    adapterMediator.deleteSavedTime(timeZoneId, millis);
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }
}
