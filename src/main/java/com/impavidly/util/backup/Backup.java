package com.impavidly.util.backup;

import java.io.FileNotFoundException;

import com.impavidly.util.backup.config.Config;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.parser.ParserException;

public class Backup {
    Config config = null;

    public Backup(String configFilePathName) throws FileNotFoundException, ParserException, ConstructorException {
        config = new Config(configFilePathName);
    }

    public void run() {

    }
}
