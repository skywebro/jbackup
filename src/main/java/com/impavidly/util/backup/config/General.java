package com.impavidly.util.backup.config;

public class General {
    private Integer retries;
    private Integer threads;

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threadCount) {
        this.threads = threadCount;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }
}
