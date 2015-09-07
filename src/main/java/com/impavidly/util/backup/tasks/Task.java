package com.impavidly.util.backup.tasks;

public abstract class Task extends Thread {
    protected com.impavidly.util.backup.config.Task config = null;
    protected Object context = null;

    @Override
    public void run() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setConfig(com.impavidly.util.backup.config.Task config) {
        this.config = config;
    }

    public com.impavidly.util.backup.config.Task getConfig() {
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
