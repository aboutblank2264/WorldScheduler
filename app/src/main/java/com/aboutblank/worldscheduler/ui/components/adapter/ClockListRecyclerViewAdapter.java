package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
        holder.setClockInfo(clock);
        holder.setExpanded(adapterMediator.getCurrentExpandedPosition() == position);

        holder.setOnClickListener(adapterMediator.getOnClickListener());
    }

    public void update(List<Clock> newClocks) {
        clocks.clear();
        clocks.addAll(newClocks);
        notifyDataSetChanged();
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

        @BindView(R.id.item_menu)
        TextView menuButton;

        ClockListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            setMenuButton();
        }

        void setClockInfo(@NonNull Clock clock) {
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
                            switch(item.getItemId()) {
                                case R.id.add_new:
                                    Log.d(LOG, "Add New");
                                    break;
                                case R.id.delete:
                                    Log.d(LOG, "Delete");
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
        }

        boolean isExpanded() {
            return false;
        }
    }
}
