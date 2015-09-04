package com.impavidly.util.backup.observers;

import java.util.Observable;
import com.impavidly.util.backup.config.Observer;

public abstract class Base implements java.util.Observer {
    protected Observer config = null;

    public Base(Observer config) {
        setConfig(config);
    }

    @Override
    public void update(Observable o, Object arg) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setConfig(Observer config) {
        this.config = config;
    }

    public Observer getConfig() {
        return config;
    }

    public String getClassName() {
        return this.getClass().getSimpleName();
    }
}
