package com.impavidly.util.backup.observers;

import java.util.Observable;
import com.impavidly.util.backup.annotations.Observer;

@Observer(strategy = "mysql")
public class Mysql extends Base {
    @Override
    public void update(Observable o, Object arg) {
    }
}
