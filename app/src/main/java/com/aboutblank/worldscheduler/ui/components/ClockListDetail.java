package com.aboutblank.worldscheduler.ui.components;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.aboutblank.worldscheduler.R;
import com.aboutblank.worldscheduler.ui.components.adapter.ClockListDetailRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockListDetail extends ConstraintLayout {
    @BindView(R.id.detail_saved)
    RecyclerView saved;

    @BindView(R.id.detail_add)
    Button addButton;

    @BindView(R.id.detail_delete)
    Button deleteButton;

    private OnDeleteClickedListener onDeleteClickedListener;
    private OnAddClickedListener onAddClickedListener;

    public ClockListDetail(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        View root = inflate(context, R.layout.clock_list_detail_layout, this);
        ButterKnife.bind(root);

        ClockListDetailRecyclerViewAdapter adapter = new ClockListDetailRecyclerViewAdapter();
        saved.setAdapter(adapter);
        saved.setLayoutManager(new LinearLayoutManager(context));

        initializeDeleteButton();
        initializeAddButton();
    }

    private void initializeDeleteButton() {
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onDeleteClickedListener != null) {
                    onDeleteClickedListener.onDelete();
                }
            }
        });
    }

    private void initializeAddButton() {
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onAddClickedListener != null) {
                    onAddClickedListener.onAdd();
                }
            }
        });
    }

    public void setOnDeleteClickedListener(OnDeleteClickedListener listener) {
        this.onDeleteClickedListener = listener;
    }

    public void setOnAddClickedListener(OnAddClickedListener listener) {
        this.onAddClickedListener = listener;
    }

    public interface OnDeleteClickedListener {
        void onDelete();
    }

    public interface OnAddClickedListener {
        void onAdd();
    }
}
