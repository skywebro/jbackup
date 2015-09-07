package com.impavidly.util.backup.tasks;

import com.impavidly.util.backup.config.Task;

public abstract class Base extends Thread {
    protected Task config = null;
    protected Object context = null;

    @Override
    public void run() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setConfig(Task config) {
        this.config = config;
    }

    public Task getConfig() {
        return config;
    }

    public void setContext(Object context) {
        this.context = context;
    }

    public Object getContext() {
        return context;
    }

    public String getClassName() {
        return this.getClass().getSimpleName();
    }
}
