package com.unipi.xa_gm;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

public class Parser {

    private String name;
    private long process_time=0;
    private List<String> dependencies= Collections.emptyList();

    //TODO Add default Constructor with the default values

    public static CustomThread[] json() throws FileNotFoundException {

        String path = "src/com/unipi/xa_gm/data/model.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        Gson g = new Gson();
        Parser[] mp = g.fromJson(bufferedReader, Parser[].class);


        CustomThread[] ctList = new CustomThread[mp.length];
        for (int i=0; i<mp.length; i++){
            //Uncomment to check json parse
//            System.out.println("json -> name: "+mp[i].name+", process_time: "+ mp[i].process_time+", dependencies: "+mp[i].dependencies+ System.lineSeparator());
            ctList[i] = new CustomThread.Builder()
                    .name(mp[i].name)
                    .process_time(mp[i].process_time)
                    .dependency_list_str(mp[i].dependencies)
                    .build();
            }
        return ctList;
        }

        //TODO Create txt parser

}

