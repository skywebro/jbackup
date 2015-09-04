package com.impavidly.util.backup.observers;

import java.util.Observable;
import com.impavidly.util.backup.annotations.Observer;

@Observer(strategy = "mysqldump")
public class Mysql extends Base {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(this.getStrategy().toUpperCase());
    }
}
