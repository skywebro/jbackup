package com.impavidly.util.backup.tasks;

import com.impavidly.util.backup.config.Thread;
import org.apache.commons.csv.CSVRecord;

public class Mysql extends Task {
    public Mysql(Thread config, Object context) {
        super(config, context);
    }

    @Override
    public void run() {
        System.out.println("MYSQL" + (CSVRecord)context);
    }
}
