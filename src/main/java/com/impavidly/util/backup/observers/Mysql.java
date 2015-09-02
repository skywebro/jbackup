package com.impavidly.util.backup.observers;

import java.util.Observable;
import org.apache.commons.csv.CSVRecord;

public class Mysql extends Base {
    @Override
    public void update(Observable o, Object arg) {
        CSVRecord record = (CSVRecord)arg;
        try {
            System.out.println("MySQL: " + record.get("Field 1"));
        } catch (IllegalArgumentException e) {}
    }
}
