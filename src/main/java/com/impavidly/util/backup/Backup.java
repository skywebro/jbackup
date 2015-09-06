package com.impavidly.util.backup;

import java.io.*;
import java.lang.reflect.*;
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
    protected Config config = null;
    protected List<Task> tasks = new ArrayList<>();
    protected Map<Runnable, Constructor> runnables = new HashMap<>();

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

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void run() throws UnsupportedOperationException {
        setConstructors();
        Map<String, String> csvFileNames = getConfig().getRecord().getCsvs();
        for(Map.Entry<String, String> csv : csvFileNames.entrySet()) {
            try {
                try (
                    final Reader reader = new InputStreamReader(new BOMInputStream(new FileInputStream(new File(csv.getValue()))), "UTF-8");
                    final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
                ) {
                    int threadCount = getConfig().getRecord().getGeneral().getThreadCount();
                    ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
                    for(CSVRecord record : parser) {
                        for(Map.Entry<Runnable, Constructor> ctor : runnables.entrySet()) {
                            try {
                                Task task = (Task)ctor.getValue().newInstance();
                                task.setConfig(ctor.getKey());
                                task.setContext(record);
                                executorService.execute(task);
                            } catch (ReflectiveOperationException | NullPointerException e) {
                                System.err.println("Could not instantiate " + ctor);
                            }
                        }
                    }
                    executorService.shutdown();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    protected void setConstructors() throws UnsupportedOperationException {
        Map<String, Runnable> runnable = this.getConfig().getRecord().getRunnables();

        for(Map.Entry<String, Runnable> runnableEntry : runnable.entrySet()) {
            String taskClassName = runnableEntry.getValue().getClassName();
            try {
                Class<?> clazz = Class.forName(taskClassName);
                Constructor ctor = clazz.getConstructor();
                runnables.put(runnableEntry.getValue(), ctor);
            } catch (ReflectiveOperationException e) {
                System.err.println("Could not create " + taskClassName);
            }
        }

        if (0 == runnables.size()) throw new UnsupportedOperationException("No runnable found");
    }
}
