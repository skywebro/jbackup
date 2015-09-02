package com.impavidly.util.backup.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Observer {
    private String className;
    private String command;
    private String outputPath;
    private String csvFieldsIndexes;
    private List<Integer> csvFieldsIndexesList;

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
        this.csvFieldsIndexesList = Collections.emptyList();
        try {
            //explode the indexes csv
            List<String> stringList = Arrays.asList(csvFieldsIndexes.trim().split("\\s*,\\s*"));
            this.csvFieldsIndexesList = stringList.stream().map(Integer::valueOf).collect(Collectors.toList());
        } catch (NullPointerException e) {
            //it's already set to empty list
        }
    }

    public List<Integer> getCsvFieldsIndexesList() {
        return csvFieldsIndexesList;
    }
}
