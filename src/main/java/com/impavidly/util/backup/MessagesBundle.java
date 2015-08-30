package com.impavidly.util.backup;

import java.util.ResourceBundle;

public class MessagesBundle {
    private static final String BASE_NAME = "i18n.backup";
    private static ResourceBundle labels;

    static {
        MessagesBundle.labels = ResourceBundle.getBundle(MessagesBundle.BASE_NAME);
    }

    public static String getString(String key) {
        return MessagesBundle.labels.getString(key);
    }
}
