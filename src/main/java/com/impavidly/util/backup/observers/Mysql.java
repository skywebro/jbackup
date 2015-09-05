package com.impavidly.util.backup.observers;

import java.io.*;
import java.util.Observable;
import org.apache.commons.csv.CSVRecord;
import com.impavidly.util.backup.config.Observer;

public class Mysql extends Base {
    public Mysql(Observer config) {
        super(config);
    }

    @Override
    public void update(Observable o, Object arg) {
        CSVRecord record = (CSVRecord)arg;
        try {
            File out = new File(getConfig().getOutputPath() + "/" + record.get(0) + ".txt");
            out.createNewFile();
        } catch (IOException e) {}
    }
}
