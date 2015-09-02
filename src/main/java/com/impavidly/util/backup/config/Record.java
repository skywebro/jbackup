package com.impavidly.util.backup.config;

import java.util.Map;

public class Record {
    private General general;
    private Map<String, Observer> observers;
    private Map<String, String> csvs;

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }

    public Map<String, Observer> getObservers() {
        return observers;
    }

    public void setObservers(Map<String, Observer> observers) {
        this.observers = observers;
    }

    public Map<String, String> getCsvs() {
        return csvs;
    }

    public void setCsvs(Map<String, String> csvs) {
        this.csvs = csvs;
    }
}
