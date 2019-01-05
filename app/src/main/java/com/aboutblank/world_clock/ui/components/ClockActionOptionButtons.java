package com.aboutblank.world_clock.ui.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.aboutblank.world_clock.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockActionOptionButtons extends ConstraintLayout {

    @BindView(R.id.option_schedule_new)
    Button schedule;

    @BindView(R.id.option_delete)
    Button delete;

    public ClockActionOptionButtons(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        View view = inflate(context, R.layout.clock_action_option_buttons, this);

        ButterKnife.bind(view);
    }

    public void setScheduleOnClick(@NonNull final OnClickListener listener) {
        schedule.setOnClickListener(listener);
    }

    public void setDeleteOnClick(@NonNull final OnClickListener listener) {
        delete.setOnClickListener(listener);
    }
}
