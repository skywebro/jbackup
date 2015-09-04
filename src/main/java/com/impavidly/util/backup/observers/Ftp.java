package com.impavidly.util.backup.observers;

import java.util.Observable;
import com.impavidly.util.backup.config.Observer;

public class Ftp extends Base {
    public Ftp(Observer config) {
        super(config);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(this.getClassName().toUpperCase());
    }
}
