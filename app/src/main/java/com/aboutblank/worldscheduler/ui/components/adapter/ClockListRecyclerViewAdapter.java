package com.aboutblank.worldscheduler.ui.components.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.components.SimpleDateClock;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockListRecyclerViewAdapter extends RecyclerView.Adapter<ClockListRecyclerViewAdapter.ClockListHolder> {
    private final static String LOG = ClockListRecyclerViewAdapter.class.getSimpleName();

    private ClockListAdapterMediator adapterMediator;

    private RecyclerView.RecycledViewPool recycledViewPool;

    public ClockListRecyclerViewAdapter(ClockListAdapterMediator adapterMediator) {
        this.adapterMediator = adapterMediator;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recycledViewPool = recyclerView.getRecycledViewPool();
    }

    @NonNull
    @Override
    public ClockListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_clock_list_item, parent, false);
        return new ClockListHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull final ClockListHolder holder, final int position) {
        Clock clock = adapterMediator.getClockAt(position);
        holder.setClockInfo(clock);
        holder.setExpanded(adapterMediator.getCurrentExpandedPosition() == position);

        holder.setOnClickListener(adapterMediator.getOnClickListener());
    }

    @Override
    public int getItemCount() {
        return adapterMediator.getClockCount();
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

        @BindView(R.id.item_menu)
        TextView menuButton;

        @BindView(R.id.item_saved_time_rv)
        RecyclerView savedTimeRecyclerView;
        ClockListDetailRecyclerViewAdapter adapter;

        private List<Long> savedTimes;

        ClockListHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            setMenuButton();

            adapter = new ClockListDetailRecyclerViewAdapter(adapterMediator);
            savedTimeRecyclerView.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            savedTimeRecyclerView.setAdapter(adapter);
        }

        void setClockInfo(@NonNull Clock clock) {
            Log.d(LOG, clock.toString());
            savedTimes = clock.getSavedTimes() == null ? new ArrayList<Long>() : clock.getSavedTimes();

            timeZone.setText(clock.getName());

            timeZoneCompare.setText(adapterMediator.getOffSetString(clock.getTimeZoneId()));
            simpleDateClock.setTimeZone(clock.getTimeZoneId());
        }

        void setOnClickListener(View.OnClickListener listener) {
            layout.setOnClickListener(listener);
        }

        void setMenuButton() {
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PopupMenu popupMenu = adapterMediator.getPopupMenu(menuButton);
                    popupMenu.inflate(R.menu.clock_list_side_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.add_new:
                                    Log.d(LOG, "Add New");
                                    adapterMediator.addNew(ClockListHolder.this.getAdapterPosition());
                                    break;
                                case R.id.delete:
                                    Log.d(LOG, "Delete");
                                    adapterMediator.onDelete(ClockListHolder.this.getAdapterPosition());
                                    break;
                            }
                            return false;
                        }
                    });

                    popupMenu.show();
                }
            });
        }

        void setExpanded(boolean activated) {
            if (savedTimes.size() > 0) {
                Log.d(LOG, String.valueOf(activated));
                savedTimeRecyclerView.setVisibility(activated ? View.VISIBLE : View.GONE);
                if (activated) {
                    Log.d(LOG, savedTimes.toString());
                    adapter.update(savedTimes);
                }
            }
        }

        boolean isExpanded() {
            return savedTimeRecyclerView.getVisibility() == View.VISIBLE;
        }
    }
}
