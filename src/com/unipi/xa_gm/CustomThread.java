package com.unipi.xa_gm;


import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;


public class CustomThread extends Thread{

    private long processTime;
    private List<CustomThread> dependencyList = new ArrayList<>();

    private final long startTime = System.currentTimeMillis();
    private long executionTime;
    private long idleTime;

    public long getExecutionTime() {return executionTime;}

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }
    public long getProcessTime() {
        return processTime;
    }

    public List<CustomThread> getDependencyList() {
        return this.dependencyList;
    }
    public void removeDependency(String customThread) {
        this.dependencyList.removeIf(x->x.getName().equals(customThread));
    }

    public CustomThread(String name) {
        super(name);
        this.processTime = 0;
    }

    //Builder constructor (Constructor overloading)
    public CustomThread(Builder builder) {
        this.processTime =builder.processTime;
        this.dependencyList =builder.dependencyList;
        super.setName(builder.name);
    }

    //TODO keep build pattern to theory
    /*
        Builder Design Pattern.
        In this class it is used mainly for educational purposes
    */
    public static class Builder {
        private String name;
        private long processTime;
        private List<CustomThread> dependencyList;

        public void setProcessTime(long processTime) {
            this.processTime = processTime;
        }
        public long getProcessTime() {
            return processTime;
        }

        public Builder() {
            this.processTime = 0;
            this.dependencyList = Collections.emptyList();
        }

        public Builder processTime(long processTime) {
            this.processTime = processTime;
            return this;
        }

        public Builder dependencyListStr(List<String> dependencies) {
            List<CustomThread> threadList = new ArrayList<CustomThread>();
            for (String threadName : dependencies) {
                CustomThread thread = new CustomThread.Builder()
                        .name(threadName)
                        .build();
                threadList.add(thread);
            }
            this.dependencyList =threadList;
            return this;
        }

        public Builder name (String name) {
            this.name = name;
            return this;
        }

        public CustomThread build(){
            return new CustomThread(this);
        }
    }

    //Method Overriding
    public void run(){

        //TODO measure idle time instead of adding it
        try {
            idleTime=System.currentTimeMillis() - startTime;
            for (CustomThread dependency:dependencyList){
                idleTime +=dependency.getProcessTime();
                dependency.join(idleTime);
            }
            Thread.sleep(processTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //TODO handle the exception
        }
        executionTime=System.currentTimeMillis() - startTime;
        //Print CurrentThread
        print();
    }

    //TODO fix this a little
     public void print(){
        System.out.println(MessageFormat.format("{1} ({0}) finished! Execution time: {2} sec, waited idle for:{3} sec, Process_time:{4} sec.",
                 Thread.currentThread().getId(),Thread.currentThread().getName(),executionTime,idleTime,
                CustomThread.this.getProcessTime()));
    }
}
