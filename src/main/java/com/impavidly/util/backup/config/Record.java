package com.impavidly.util.backup.config;

import java.util.Map;

public class Record {
    private General general;
    private Map<String, Thread> threads;
    private Map<String, String> csvs;

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }

    public Map<String, Thread> getThreads() {
        return threads;
    }

    public void setThreads(Map<String, Thread> tasks) {
        this.threads = tasks;
    }

    public Map<String, String> getCsvs() {
        return csvs;
    }

    public void setCsvs(Map<String, String> csvs) {
        this.csvs = csvs;
    }
}
