package com.aboutblank.worldscheduler.ui.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextClock;

import com.aboutblank.worldscheduler.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleDateClock extends ConstraintLayout {

    @BindView(R.id.clock_main)
    TextClock main;
    @BindView(R.id.clock_date)
    TextClock date;

    public SimpleDateClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public void setTimeZone(String timeZone) {
        main.setTimeZone(timeZone);
        date.setTimeZone(timeZone);
    }

    private void initialize(Context context) {
        View root = inflate(context, R.layout.simple_clock_layout, this);
        ButterKnife.bind(root);

        main.setFormat12Hour(context.getString(R.string.default_clock_format_12hr));
        date.setFormat12Hour(context.getString(R.string.default_date_format));
    }
}
