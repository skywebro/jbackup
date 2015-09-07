package com.impavidly.util.backup;

import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.*;
import org.apache.commons.io.input.BOMInputStream;
import org.yaml.snakeyaml.error.MarkedYAMLException;

import com.impavidly.util.backup.config.Config;
import com.impavidly.util.backup.config.Runnable;
import com.impavidly.util.backup.tasks.Task;

public class Backup {
    protected Config config;
    protected Map<Runnable, Constructor> runnables;

    public Backup(String configFilePathName) throws IOException, MarkedYAMLException {
        setConfig(configFilePathName);
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(String configFilePathName) throws IOException, MarkedYAMLException, UnsupportedOperationException {
        this.config = new Config(configFilePathName);
        Bootstrap.run(this);
    }

    public void setConfig(Config config) throws IOException, UnsupportedOperationException {
        this.config = config;
        Bootstrap.run(this);
    }

    public void run() throws UnsupportedOperationException {
        int threadCount = getConfig().getRecord().getGeneral().getThreads();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        Map<String, String> csvFileNames = getConfig().getRecord().getCsvs();

        for(Map.Entry<String, String> csv : csvFileNames.entrySet()) {
            try {
                try (
                    final Reader reader = new InputStreamReader(new BOMInputStream(new FileInputStream(new File(csv.getValue()))), "UTF-8");
                    final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
                ) {
                    for(CSVRecord record : parser) {
                        for(Map.Entry<Runnable, Constructor> ctor : getRunnables().entrySet()) {
                            try {
                                Task task = (Task)ctor.getValue().newInstance();
                                task.setConfig(ctor.getKey());
                                task.setContext(record);
                                executorService.execute(task);
                            } catch (ReflectiveOperationException | NullPointerException e) {
                                System.err.println("Error executing " + ctor);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        executorService.shutdown();
    }

    public void setRunnables(Map<Runnable, Constructor> runnables) {
        this.runnables = runnables;
    }

    public Map<Runnable, Constructor> getRunnables() {
        return runnables;
    }

    static class Bootstrap {
        static void run(Backup backup) throws IOException, UnsupportedOperationException {
            prepareTasksConstructors(backup);
        }

        static void prepareTasksConstructors(Backup backup) throws IOException, UnsupportedOperationException {
            Map<String, Runnable> runnablesConfig = backup.getConfig().getRecord().getRunnables();
            Date now = new Date();
            backup.setRunnables(new HashMap<>());

            for(Map.Entry<String, Runnable> runnableConfigEntry : runnablesConfig.entrySet()) {
                String taskClassName = runnableConfigEntry.getValue().getClassName();
                try {
                    String outputPath = parseOutputPath(runnableConfigEntry.getValue().getOutputPath(), now);
                    runnableConfigEntry.getValue().setOutputPath(outputPath);

                    Class<?> clazz = Class.forName(taskClassName);
                    Constructor ctor = clazz.getConstructor();
                    backup.getRunnables().put(runnableConfigEntry.getValue(), ctor);

                    try {
                        Path resultPath = FileSystems.getDefault().getPath(outputPath).normalize();
                        Files.createDirectories(resultPath);
                        if (!Files.isDirectory(resultPath) || !Files.isWritable(resultPath)) {
                            throw new IOException(outputPath + " is not a directory or it's not writable");
                        }
                    } catch (FileAlreadyExistsException | InvalidPathException e) {
                        throw new IOException(e);
                    }
                } catch (ReflectiveOperationException e) {
                    System.err.println("Could not create " + taskClassName);
                }
            }

            if (0 == backup.getRunnables().size()) throw new UnsupportedOperationException("No runnable found");
        }

        static String parseOutputPath(String outputPath, Date date) throws UnsupportedOperationException {
            String regex = "\\{\\$date\\(([GyMdHhmSsEDFwWakKz \\-_]*)\\)\\}";
            Pattern p = Pattern.compile(regex);
            Matcher matcher = p.matcher(outputPath);
            while (matcher.find()) {
                String match = matcher.group(1);
                String dateFormat = (0 < match.length()) ? match : "yyyyMMddHHmmss";
                String dateString = (new SimpleDateFormat(dateFormat)).format(date);
                outputPath = outputPath.replace(matcher.group(0), dateString);
            }

            if (outputPath.toLowerCase().contains("$date")) {
                throw new UnsupportedOperationException("Unrecognizable date format in " + outputPath);
            }

            return outputPath;
        }
    }
}
