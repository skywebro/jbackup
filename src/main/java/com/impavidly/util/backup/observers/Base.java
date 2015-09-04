package com.impavidly.util.backup.observers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import com.impavidly.util.backup.Backup;
import com.impavidly.util.backup.annotations.Observer;

public abstract class Base implements java.util.Observer {
    protected String strategy;

    public Base() throws NullPointerException {
        Observer annotation = this.getClass().getAnnotation(Observer.class);
        this.setStrategy(annotation.strategy());
    }

    @Override
    public void update(Observable o, Object arg) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    protected List<Integer> getCsvFieldsIndexes(Backup backup) {
        List<Integer> fieldsIndexes = new ArrayList<>();
        try {
            fieldsIndexes = backup.getConfig().getRecord().getObservers().get(this.getStrategy()).getCsvFieldsIndexesList();
        } catch (NullPointerException e) {
            //return an empty collection if not found
        }

        return fieldsIndexes;
    }

    protected String getCommand(Backup backup) {
        String command = backup.getConfig().getRecord().getObservers().get(this.getStrategy()).getCommand();

        return command;
    }

    protected String getOutputPath(Backup backup) {
        String outputPath = backup.getConfig().getRecord().getObservers().get(this.getStrategy()).getOutputPath();

        return outputPath;
    }
}
