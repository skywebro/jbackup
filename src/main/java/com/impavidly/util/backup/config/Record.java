package com.impavidly.util.backup.config;

import java.util.Map;

public class Record {
    private General general;
    private Map<String, Runnable> runnables;
    private Map<String, String> csvs;

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }

    public Map<String, Runnable> getRunnables() {
        return runnables;
    }

    public void setRunnables(Map<String, Runnable> tasks) {
        this.runnables = tasks;
    }

    public Map<String, String> getCsvs() {
        return csvs;
    }

    public void setCsvs(Map<String, String> csvs) {
        this.csvs = csvs;
    }
}
