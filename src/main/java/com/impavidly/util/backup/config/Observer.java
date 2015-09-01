package com.impavidly.util.backup.config;

public class Observer {
    private String className;
    private String command;
    private String outputPath;
    private String csvFieldsIndexes;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getCsvFieldsIndexes() {
        return csvFieldsIndexes;
    }

    public void setCsvFieldsIndexes(String csvFieldsIndexes) {
        this.csvFieldsIndexes = csvFieldsIndexes;
    }
}
