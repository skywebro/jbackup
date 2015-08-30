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
    private static final String OPTION_NAME = "yaml";
    private static Options options = new Options();

    static {
        Main.options.addOption(Main.OPTION_NAME, true, MessagesBundle.getString("program_usage_message"));
    }

    private static CommandLine getCommandLine(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(Main.options, args);

        if (!cmd.hasOption(Main.OPTION_NAME)) throw new ParseException("No YAML file specified");

        return cmd;
    }

    public static void main(String[] args) {
        String yamlFile = "";
        try {
            CommandLine cmd = Main.getCommandLine(args);
            yamlFile = cmd.getOptionValue(Main.OPTION_NAME);
            Yaml yaml = new Yaml();
            Object data = yaml.load(new FileInputStream(new File(yamlFile)));
            System.out.println(yaml.dump(data));
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(new Object(){}.getClass().getEnclosingClass().getName(), Main.options);
        } catch (FileNotFoundException e) {
            System.err.println(String.format(MessagesBundle.getString("file_not_found"), yamlFile));
        }
    }
}
