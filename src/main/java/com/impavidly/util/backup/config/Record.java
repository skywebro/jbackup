package com.impavidly.util.backup.config;

import java.util.Map;

public class Record {
    private Map<String, String> general;
    private Map<String, Observer> observers;
    private Map<String, String> csv;

    public Map<String, String> getGeneral() {
        return general;
    }

    public void setGeneral(Map<String, String> general) {
        this.general = general;
    }

    public Map<String, Observer> getObservers() {
        return observers;
    }

    public void setObservers(Map<String, Observer> observers) {
        this.observers = observers;
    }

    public Map<String, String> getCsv() {
        return csv;
    }

    public void setCsv(Map<String, String> csv) {
        this.csv = csv;
    }
}
