package com.impavidly.util.backup.config;

public class Observer {
    private String className;
    private String command;
    private String outputPath;
    private String csvFieldNames;

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

    public String getCsvFieldNames() {
        return csvFieldNames;
    }

    public void setCsvFieldNames(String csvFieldNames) {
        this.csvFieldNames = csvFieldNames;
    }
}
