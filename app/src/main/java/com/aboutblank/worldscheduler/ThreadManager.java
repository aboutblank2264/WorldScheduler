package com.aboutblank.worldscheduler;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    private final static int THREAD_POOl = 2;

    private ExecutorService backendThread = Executors.newFixedThreadPool(THREAD_POOl);
    private Handler mainThread = new Handler(Looper.getMainLooper());

    public void execute(Runnable runnable) {
        backendThread.execute(runnable);
    }

    public void scheduleMain(Runnable runnable) {
        mainThread.post(runnable);
    }
}
