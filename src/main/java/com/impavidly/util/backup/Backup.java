package com.impavidly.util.backup;

import java.io.*;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

import org.apache.commons.csv.*;
import org.apache.commons.io.input.BOMInputStream;
import org.yaml.snakeyaml.constructor.ConstructorException;
import org.yaml.snakeyaml.parser.ParserException;

import com.impavidly.util.backup.config.Config;
import com.impavidly.util.backup.config.Runnable;
import com.impavidly.util.backup.tasks.Task;

public class Backup {
    protected Config config;
    protected Map<Runnable, Constructor> runnables;

    public Backup(String configFilePathName) throws FileNotFoundException, ParserException, ConstructorException {
        setConfig(configFilePathName);
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(String configFilePathName) throws FileNotFoundException, ParserException, ConstructorException, UnsupportedOperationException {
        this.config = new Config(configFilePathName);
        cacheTaskConstructors();
    }

    public void setConfig(Config config) throws UnsupportedOperationException {
        this.config = config;
        cacheTaskConstructors();
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

    protected void cacheTaskConstructors() throws UnsupportedOperationException {
        Map<String, Runnable> configRunnables = getConfig().getRecord().getRunnables();
        String dateString = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
        setRunnables(new HashMap<>());

        for(Map.Entry<String, Runnable> runnableEntry : configRunnables.entrySet()) {
            String taskClassName = runnableEntry.getValue().getClassName();
            try {
                String outputPath = runnableEntry.getValue().getOutputPath();
                outputPath = outputPath.replaceAll("\\{\\$date\\}", dateString);
                runnableEntry.getValue().setOutputPath(outputPath);

                Class<?> clazz = Class.forName(taskClassName);
                Constructor ctor = clazz.getConstructor();
                getRunnables().put(runnableEntry.getValue(), ctor);

                File outputDir = new File(outputPath);
                outputDir.mkdirs();
            } catch (ReflectiveOperationException e) {
                System.err.println("Could not create " + taskClassName);
            }
        }

        if (0 == getRunnables().size()) throw new UnsupportedOperationException("No runnable found");
    }
}
