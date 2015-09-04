package com.impavidly.util.backup.config;

import java.util.*;

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
        List<Integer> integerList = new ArrayList<>();
        try {
            //explode the indexes csv
            List<String> stringList = Arrays.asList(csvFieldsIndexes.trim().split("\\s*,\\s*"));
            for(String s : stringList) {
                try {
                    integerList.add(Integer.valueOf(s));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid CSV index: " + s);
                }
            }
            //this.csvFieldsIndexesList = stringList.stream().map(Integer::parseInt).collect(Collectors.toList());
        } catch (NullPointerException e) {
            //it's already set to empty list
            System.out.println(e.getMessage());
        } finally {
            this.csvFieldsIndexesList = integerList;
        }
    }

    public List<Integer> getCsvFieldsIndexesList() {
        return csvFieldsIndexesList;
    }
}
