package com.impavidly.util.backup.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.parser.ParserException;

public class Config {
    protected Record record = null;

    public Config(String configFilePathName) throws FileNotFoundException, ParserException, ConstructorException {
        Yaml yaml = new Yaml(new Constructor(Record.class));
        record = (Record)yaml.load(new FileInputStream(new File(configFilePathName)));
    }

    public Record getRecord() {
        return record;
    }
}