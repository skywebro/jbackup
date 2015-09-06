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
import com.impavidly.util.backup.config.Thread;
import com.impavidly.util.backup.tasks.Task;

public class Backup {
    protected Config config = null;
    protected List<Task> tasks = new ArrayList<>();

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
        Map<String, String> csvFileNames = getConfig().getRecord().getCsvs();
        for(Map.Entry<String, String> csv : csvFileNames.entrySet()) {
            try {
                try (
                    final Reader reader = new InputStreamReader(new BOMInputStream(new FileInputStream(new File(csv.getValue()))), "UTF-8");
                    final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
                ) {
                    Map<String, Thread> threads = this.getConfig().getRecord().getThreads();
                    int threadCount = getConfig().getRecord().getGeneral().getThreadCount();
                    ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
                    for(CSVRecord record : parser) {
                        for(Map.Entry<String, Thread> thread : threads.entrySet()) {
                            String taskClassName = thread.getValue().getClassName();
                            try {
                                Class<?> clazz = Class.forName(taskClassName);
                                Constructor ctor = clazz.getConstructor(Thread.class, Object.class);
                                Task task = (Task)ctor.newInstance(thread.getValue(), record);
                                executorService.execute(task);
                            } catch (ReflectiveOperationException | NullPointerException e) {
                                System.err.println("Could not instantiate " + taskClassName);
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
}
