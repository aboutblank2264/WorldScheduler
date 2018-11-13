package com.aboutblank.worldscheduler.ui.components.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.worldscheduler.R;

public class ClockListDetailRecyclerViewAdapter extends RecyclerView.Adapter<ClockListDetailRecyclerViewAdapter.ClockListDetailViewHolder> {


    @NonNull
    @Override
    public ClockListDetailViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_clock_detail_item, parent, false);
        return new ClockListDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClockListDetailViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ClockListDetailViewHolder extends RecyclerView.ViewHolder {

        public ClockListDetailViewHolder(final View itemView) {
            super(itemView);
        }
    }
}
