package com.impavidly.util.backup.observers;

import com.impavidly.util.backup.Backup;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class Base implements Observer {
    public void update(Observable o, Object arg) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    protected String getObserverName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    protected List<Integer> getCsvFieldIndexes(Backup backup) {
        List<Integer> fields = Collections.emptyList();
        try {
            fields = backup.getConfig().getRecord().getObservers().get(this.getObserverName()).getCsvFieldsIndexesList();
        } catch (NullPointerException e) {
            //return an empty collection if not found
        }

        return fields;
    }
}
