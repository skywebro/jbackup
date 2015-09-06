package com.impavidly.util.backup.tasks;

import com.impavidly.util.backup.config.Runnable;

public abstract class Task extends Thread {
    protected Runnable config = null;
    protected Object context = null;

    @Override
    public void run() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setConfig(Runnable config) {
        this.config = config;
    }

    public Runnable getConfig() {
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
