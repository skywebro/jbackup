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

public final class Backup {
    private static final String OPTION_NAME = "yaml";
    private static Options options = new Options();

    static {
        Backup.options.addOption(Backup.OPTION_NAME, true, MessagesBundle.getString("program_usage_message"));
    }

    public static void main(String[] args) {
        String configFilePathName = "";

        try {
            configFilePathName = Backup.getConfigurationFilePathName(args);
            Yaml yaml = new Yaml();
            Object data = yaml.load(new FileInputStream(new File(configFilePathName)));
            System.out.println(yaml.dump(data));
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(new Object(){}.getClass().getEnclosingClass().getName(), Backup.options);
        } catch (FileNotFoundException e) {
            System.err.println("[ERROR] " + e.getLocalizedMessage());
        }
    }

    private static String getConfigurationFilePathName(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(Backup.options, args);

        if (!cmd.hasOption(Backup.OPTION_NAME)) throw new ParseException("No configuration file present");

        return cmd.getOptionValue(Backup.OPTION_NAME);
    }
}
