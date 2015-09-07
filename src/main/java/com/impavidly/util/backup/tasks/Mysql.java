package com.impavidly.util.backup.tasks;

import org.apache.commons.csv.CSVRecord;

public class Mysql extends Base {
    @Override
    public void run() {
        System.out.println(this.getClassName().toUpperCase() + (CSVRecord)context);
    }
}
