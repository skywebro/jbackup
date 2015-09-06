package com.impavidly.util.backup.tasks;

import java.io.File;
import com.impavidly.util.backup.config.Thread;

public abstract class Task extends java.lang.Thread {
    protected Thread config = null;
    protected Object context = null;

    public Task(Thread config, Object context) throws SecurityException {
        setConfig(config);
        setContext(context);
        File outputPath = new File(config.getOutputPath());
        outputPath.mkdirs();
    }

    @Override
    public void run() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setConfig(Thread config) {
        this.config = config;
    }

    public Thread getConfig() {
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
