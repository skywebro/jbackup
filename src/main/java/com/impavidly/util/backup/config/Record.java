package com.impavidly.util.backup.config;

import java.util.Map;

public class Record {
    private General general;
    private Map<String, Runnable> tasks;
    private Map<String, String> csvs;

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }

    public Map<String, Runnable> getTasks() {
        return tasks;
    }

    public void setTasks(Map<String, Runnable> tasks) {
        this.tasks = tasks;
    }

    public Map<String, String> getCsvs() {
        return csvs;
    }

    public void setCsvs(Map<String, String> csvs) {
        this.csvs = csvs;
    }
}
