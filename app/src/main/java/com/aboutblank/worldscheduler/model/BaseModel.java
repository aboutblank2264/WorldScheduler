package com.aboutblank.worldscheduler.model;

public abstract class BaseModel {
    protected final boolean loading;
    protected final Throwable error;

    public BaseModel(boolean loading, Throwable error) {
        this.loading = loading;
        this.error = error;
    }
}
