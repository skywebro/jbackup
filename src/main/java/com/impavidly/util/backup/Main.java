package com.impavidly.util.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.yaml.snakeyaml.Yaml;

public final class Main {
    private static String optionName = "yaml";
    private static Options options = new Options();

    static {
        Main.options.addOption(Main.optionName, true, "The YAML configuration file path.");
    }

    private static CommandLine getCommandLine(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(Main.options, args);

        if (!cmd.hasOption(Main.optionName)) throw new ParseException("No YAML file specified");

        return cmd;
    }

    public static void main(String[] args) {
        String yamlFile = "";

        try {
            CommandLine cmd = Main.getCommandLine(args);
            yamlFile = cmd.getOptionValue(Main.optionName);
            Yaml yaml = new Yaml();
            Object data = yaml.load(new FileInputStream(new File(yamlFile)));
            System.out.print(yaml.dump(data));
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(new Object(){}.getClass().getEnclosingClass().getName(), Main.options);
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: The file " + yamlFile + " was not found.");
        }
    }
}
