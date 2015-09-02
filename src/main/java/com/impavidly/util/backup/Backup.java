package com.impavidly.util.backup;

import java.io.*;
import java.util.Map;
import java.util.Observable;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.parser.ParserException;

import com.impavidly.util.backup.config.Config;
import com.impavidly.util.backup.config.Observer;

public class Backup extends Observable {
    protected Config config = null;

    public Backup(String configFilePathName) throws FileNotFoundException, ParserException, ConstructorException {
        setConfig(configFilePathName);
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(String configFilePathName) throws FileNotFoundException, ParserException, ConstructorException {
        this.config = new Config(configFilePathName);
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void run() throws UnsupportedOperationException {
        this.addObservers();

        Map<String, String> csvFileNames = this.getConfig().getRecord().getCsvs();
        for(Map.Entry<String, String> entry : csvFileNames.entrySet()) {
            try {
                final Reader reader = new InputStreamReader(new BOMInputStream(new FileInputStream(entry.getValue())), "UTF-8");
                final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
                try {
                    for (CSVRecord record : parser) {
                        this.setChanged();
                        this.notifyObservers(record);
                    }
                } finally {
                    parser.close();
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    protected void addObservers() throws UnsupportedOperationException {
        Map<String, Observer> observers = this.getConfig().getRecord().getObservers();

        for(Map.Entry<String, Observer> entry : observers.entrySet()) {
            String observerClassName = entry.getValue().getClassName();
            try {
                java.util.Observer observer = (java.util.Observer)Class.forName(observerClassName).newInstance();
                this.addObserver(observer);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                System.err.println("Could not instantiate " + observerClassName);
            }
        }

        if (0 == this.countObservers()) throw new UnsupportedOperationException("No observers found.");
    }
}
