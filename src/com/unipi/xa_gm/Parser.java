package com.unipi.xa_gm;

import com.google.gson.Gson;

import java.util.List;

public class Parser {

    private String name;
    private long process_time;
    private List<String> dependencies;

    public static void json(){
        Gson g = new Gson();
        String jsonString="       {\n" +
                "            \"name\" :\"P6\",\n" +
                "            \"process_time\" : 2345,\n" +
                "            \"dependencies\": [\"P3\",\"P5\"]\n" +
                "        }";
        Parser mp = g.fromJson(jsonString, Parser.class);

        System.out.println("\t\tChecking json parse...");
        System.out.println("name: "+mp.name+", process_time: "+ mp.process_time+", dependencies: "+mp.dependencies+ System.lineSeparator());

        CustomThread ct = new CustomThread.Builder()
                .name(mp.name)
                .process_time(mp.process_time)
                .dependency_list_str(mp.dependencies)
                .build();
        ct.start();
        }
}

