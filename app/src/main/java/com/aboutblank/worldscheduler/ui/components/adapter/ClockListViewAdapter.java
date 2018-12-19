package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.backend.room.Clock;
import com.aboutblank.worldscheduler.ui.components.ClockActionOptionButtons;
import com.aboutblank.worldscheduler.ui.components.IconPopupMenu;
import com.aboutblank.worldscheduler.ui.components.SimpleDateClock;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockListViewAdapter
        extends ExpandableRecyclerViewAdapter<ClockListViewAdapter.ClockViewHolder, ClockListViewAdapter.SavedTimeViewHolder> {

    private final static String LOG = ClockListRecyclerViewAdapter.class.getSimpleName();

    private ClockListAdapterMediator adapterMediator;

    public ClockListViewAdapter() {
        super(new ArrayList<ClockGroup>());
    }

    public void update(final List<Clock> clocks) {
        //TODO turn into groups
    }

    @Override
    public ClockViewHolder onCreateGroupViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_clock_list_item, parent, false);
        return new ClockViewHolder(view);
    }

    @Override
    public SavedTimeViewHolder onCreateChildViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_clock_detail_item, parent, false);
        return new SavedTimeViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(final ClockViewHolder holder, final int flatPosition, final ExpandableGroup group) {
        holder.setClockInfo(group.getTitle());
    }

    @Override
    public void onBindChildViewHolder(final SavedTimeViewHolder holder, final int flatPosition, final ExpandableGroup group,
                                      final int childIndex) {
        holder.setMillis((long) group.getItems().get(childIndex));
    }

    /* ------------------------------------------------- */
    /* -------------- ClockViewHolder ------------------ */
    /* ------------------------------------------------- */
    public class ClockViewHolder extends GroupViewHolder {

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
        private String timeZoneId;

        public ClockViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            setOptionButtonListeners();
        }

        void setClockInfo(@NonNull String timeZoneId) {
            Log.d(LOG, timeZoneId);
            this.timeZoneId = timeZoneId;

            timeZone.setText(timeZoneId);
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
                    Log.d(LOG, "Add New");
                    adapterMediator.popupNewSaveTime(getAdapterPosition());
                }
            });

            clockActionOptionButtons.setDeleteOnClick(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Log.d(LOG, "Delete");
                    adapterMediator.deleteClock(getAdapterPosition());
                }
            });
        }
    }

    /* ------------------------------------------------- */
    /* ------------ SavedTimeViewHolder ---------------- */
    /* ------------------------------------------------- */
    public class SavedTimeViewHolder extends ChildViewHolder {
        @BindView(R.id.detail_local_clock)
        TextView localClock;

        @BindView(R.id.detail_other_clock)
        TextView otherClock;

        @BindView(R.id.detail_options)
        Button optionsButton;

        private long millis;

        public SavedTimeViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            setMenuButton();
        }

        void setMillis(long time) {
            this.millis = time;
            String[] timeStrings = adapterMediator.getTimeStrings(millis);
            localClock.setText(timeStrings[0]);
            otherClock.setText(timeStrings[1]);
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
                                    adapterMediator.addAlarm(String.valueOf(localClock.getText()));
                                    break;
                                case R.id.delete:
                                    Log.d(LOG, "Delete");
                                    adapterMediator.deleteSavedTime(getAdapterPosition());
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
