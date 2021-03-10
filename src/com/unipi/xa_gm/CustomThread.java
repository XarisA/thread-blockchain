package com.unipi.xa_gm;


import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class CustomThread extends Thread{

    private Random rand = new Random(System.currentTimeMillis());

    private final long process_time;
    private final List<CustomThread> dependency;

    public CustomThread(Builder builder) {
        this.process_time=builder.process_time;
        this.dependency=builder.dependency_list;
        super.setName(builder.name);
    }

    /*
        Builder Design Pattern.
    */
    public static class Builder {
        private long process_time;
        private List<CustomThread> dependency_list;
        private String name;

        public Builder() {
            this.process_time = 0;
            this.dependency_list = Collections.emptyList();
        }

        public Builder process_time (long process_time) {
            this.process_time = process_time;
            return this;
        }

        public Builder dependency_list (List<CustomThread> dependency_list) {
            this.dependency_list = dependency_list;
            for(CustomThread ct: dependency_list)
                this.dependency_list.add(ct);
            return this;
        }

        public Builder dependency (CustomThread ct) {
                this.dependency_list.add(ct);
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

    public void run(){
        //TODO Remove this workload
        //workload begin
        for (int i=0;i<100000000 ;i++){
            rand.nextInt();
        }
        print();
        //workload end
        System.out.println("Process_time"+process_time);
        try {
            Thread.sleep(process_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //TODO handle the exception
        }
    }

    public void print(){
        System.out.println(MessageFormat.format("{1} ({0}), priotity:{2}, state:{3},isALive:{4}, isDaemon:{5}",
                Thread.currentThread().getId(),Thread.currentThread().getName(),
                Thread.currentThread().getPriority(),Thread.currentThread().getState(),Thread.currentThread().isAlive(),Thread.currentThread().isDaemon()));
    }

}
