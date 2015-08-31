package com.impavidly.util.backup;

import java.util.ResourceBundle;
import org.jetbrains.annotations.NotNull;

public class MessagesBundle {
    private static final String BASE_NAME = "i18n.backup";
    private static ResourceBundle labels;

    static {
        MessagesBundle.labels = ResourceBundle.getBundle(MessagesBundle.BASE_NAME);
    }

    @NotNull
    public static String getString(String key) {
        String label;

        try {
            label = MessagesBundle.labels.getString(key);
        } catch (RuntimeException e) {
            System.err.println("[WARNING] No translation found for " + key);
            label = key;
        }

        return label;
    }
}
