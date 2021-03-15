package com.unipi.xa_gm;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;


public class CustomThread extends Thread{

    private final Random rand = new Random(System.currentTimeMillis());

    private long processTime;
    private List<CustomThread> dependencyList = new ArrayList<>();

    private List<CustomThread> dependencyListBak = new ArrayList<>();

    private final long startTime = System.currentTimeMillis();
    private long executionTime;
    private long idleTime;

    public long getExecutionTime() {return executionTime;}

    public void setProcessTime(long processTime) {this.processTime = processTime;}
    public long getProcessTime() {
        return processTime;
    }

    public List<CustomThread> getDependencyList() {
        return this.dependencyList;
    }
    public List<String> getDependencyListFormated() {
        return this.dependencyListBak.stream().map(x->x.getName()).collect(Collectors.toList());
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
        this.dependencyListBak.addAll(this.dependencyList);
        super.setName(builder.name);
    }

    //TODO keep build pattern to theory
    /**
     * Builder Design Pattern.
     * In this class it is used mainly for educational purposes
     **/
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

//        for (int i=0;i<100000000 ;i++){
//            rand.nextInt();
//        }
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
        System.out.println(printMessage());
        /**
         * Uncomment next line to keep log in database
         */
        sqlLog(printMessage());
    }

    public String printMessage(){
        return MessageFormat.format("{1} ({0}) finished! Execution time: {2} sec, waited idle for:{3} sec, Process time:{4} msec, Dependencies: {5}.",
                Thread.currentThread().getId(),Thread.currentThread().getName(),(float)executionTime/1000,(float)idleTime/1000,
                CustomThread.this.getProcessTime(),CustomThread.this.getDependencyListFormated());
    }

    public void sqlLog(String consoleOutput){
        /**
         * This method writes the given string to table "logs" in database
         */
        try {
            String query="insert into logs (ConsoleOutput) values (?)";

            Connection conn = SQLConnection.DBConnector();

            assert conn != null;
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1,consoleOutput);

            p.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
