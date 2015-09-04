package com.impavidly.util.backup.observers;

import java.util.List;
import java.util.Observable;
import org.apache.commons.csv.CSVRecord;
import com.impavidly.util.backup.Backup;
import com.impavidly.util.backup.annotations.Observer;

@Observer(strategy = "ftp")
public class Ftp extends Base {
    @Override
    public void update(Observable o, Object arg) {
    }
}
