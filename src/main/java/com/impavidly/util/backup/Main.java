package com.impavidly.util.backup;

import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.parser.ParserException;

public class Main {
    private static final String OPTION_NAME = "yaml";
    private static Options options = new Options();

    static {
        Main.options.addOption(Main.OPTION_NAME, true, "The YAML configuration file");
    }

    public static void main(String[] args) {
        try {
            Backup backup = new Backup(Main.getConfigurationFilePathName(args));
            backup.run();
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(new Object(){}.getClass().getEnclosingClass().getName(), Main.options);
        } catch (FileNotFoundException | ParserException | ConstructorException | UnsupportedOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String getConfigurationFilePathName(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(Main.options, args);

        if (!cmd.hasOption(Main.OPTION_NAME)) throw new ParseException("No configuration file present");

        return cmd.getOptionValue(Main.OPTION_NAME);
    }
}
