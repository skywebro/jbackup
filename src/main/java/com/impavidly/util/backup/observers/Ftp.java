package com.impavidly.util.backup.observers;

import java.util.List;
import java.util.Observable;

import com.impavidly.util.backup.Backup;
import org.apache.commons.csv.CSVRecord;

public class Ftp extends Base {
    final private String observerName = "ftp";

    @Override
    public void update(Observable o, Object arg) {
        CSVRecord record = (CSVRecord)arg;
        try {
            List<Integer> indexes = this.getCsvFieldIndexes((Backup)o);
            System.out.println(this.getObserverName().toUpperCase() + ": " + record.get(0));
        } catch (IllegalArgumentException e) {}
    }
}
