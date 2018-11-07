package com.aboutblank.worldscheduler.viewmodels;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;

public class ResourcesProvider {
    private Context context;

    public ResourcesProvider(final Context context) {
        this.context = context;
    }

    public String getString(@StringRes final int strId) {
        return context.getString(strId);
    }

    public void debug(String tag, String message) {
        Log.d(tag, message);
    }

    public void error(String tag, String message) {
        Log.e(tag, message);
    }
}
