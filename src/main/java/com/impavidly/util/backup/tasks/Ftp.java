package com.impavidly.util.backup.tasks;

import com.impavidly.util.backup.config.Thread;
import org.apache.commons.csv.CSVRecord;

public class Ftp extends Task {
    public Ftp(Thread config, Object context) {
        super(config, context);
    }

    @Override
    public void run() {
        System.out.println("FTP" + (CSVRecord)context);
    }
}
