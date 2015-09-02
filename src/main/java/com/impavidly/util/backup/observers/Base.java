package com.impavidly.util.backup.observers;

import java.util.Observable;
import java.util.Observer;

public abstract class Base implements Observer {
    @Override
    public void update(Observable o, Object arg) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
