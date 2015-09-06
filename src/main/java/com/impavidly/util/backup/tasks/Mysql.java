package com.impavidly.util.backup.tasks;

import org.apache.commons.csv.CSVRecord;

public class Mysql extends Task {
    @Override
    public void run() {
        System.out.println("MYSQL" + (CSVRecord)context);
    }
}
