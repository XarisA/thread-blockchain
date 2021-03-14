package com.unipi.xa_gm;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {


    private String name;
    private long processTime =0;
    private List<String> dependencies= Collections.emptyList();

    //TODO Add default Constructor with the default values

    public static CustomThread[] json() throws FileNotFoundException {
        /*
          This method parses the given json file and creates a list with the threads it contains
          and then sets their attributes processTime and dependencies accordingly.
         */

        String path = "src/com/unipi/xa_gm/data/model2.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        Gson g = new Gson();
        Parser[] mp = g.fromJson(bufferedReader, Parser[].class);

        CustomThread[] ctList = new CustomThread[mp.length];
        for (int i=0; i<mp.length; i++){
            //Uncomment to check json parse
//            System.out.println("json -> name: "+mp[i].name+", processTime: "+ mp[i].processTime+", dependencies: "+mp[i].dependencies+ System.lineSeparator());
            ctList[i] = new CustomThread.Builder()
                    .name(mp[i].name)
                    .processTime(mp[i].processTime)
                    .dependencyListStr(mp[i].dependencies)
                    .build();
        }
        System.out.println("File Parsed Successfully!");
        return ctList;
    }


        public static List<CustomThread> text() throws FileNotFoundException {
            /*
            This method parses the given text files, creates a list with the threads they contain
            and then sets their attributes processTime and dependencies accordingly.
            */

            String path1 = "src/com/unipi/xa_gm/data/p_timings.txt";
            String path2 = "src/com/unipi/xa_gm/data/p_precedence.txt";

            List<CustomThread> ctList = null;
            try (Stream<String> stream = Files.lines(Paths.get(path2))) {
                ctList = stream.map(line -> line.split(" "))
                        .map(mp -> {
                            if (mp.length == 1) {
                                return new CustomThread.Builder()
                                        .name(mp[0])
                                        .build();
                            } else {
                                return new CustomThread.Builder()
                                        .name(mp[0])
                                        .dependencyListStr(Arrays.stream(mp[2].split(","))
                                                .collect(Collectors.toList()))
                                        .build();
                            }
                        })
                        .collect(Collectors.toList());

                try (Stream<String> lines = Files.lines(Paths.get(path1))) {
                    List<CustomThread> finalCtList = ctList;
                    lines.map(line -> line.split(" "))
                                .filter(s -> s.length > 1)
                                .forEach(l -> {finalCtList.stream().filter( x-> x.getName().equals(l[0])).forEach(y -> y.setProcessTime(Long.parseLong(l[1])));});
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("File Parsed Successfully!");
            return ctList;
        }
}

