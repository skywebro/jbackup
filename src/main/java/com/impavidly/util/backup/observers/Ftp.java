package com.impavidly.util.backup.observers;

import java.util.Observable;
import org.apache.commons.csv.CSVRecord;

public class Ftp extends Base {
    @Override
    public void update(Observable o, Object arg) {
        CSVRecord record = (CSVRecord)arg;
        try {
            System.out.println("FTP: " + record.get("Field 1"));
        } catch (IllegalArgumentException e) {}
    }
}
