package com.aboutblank.worldscheduler;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    private ExecutorService backendThread = Executors.newSingleThreadExecutor();
    private Handler mainThread = new Handler(Looper.getMainLooper());

    public void execute(Runnable runnable) {
        backendThread.execute(runnable);
    }

    public void scheduleMain(Runnable runnable) {
        mainThread.post(runnable);
    }
}
