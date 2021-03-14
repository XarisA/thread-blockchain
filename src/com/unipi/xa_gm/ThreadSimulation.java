package com.unipi.xa_gm;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class ThreadSimulation {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

    	//TODO add command line arguments for json or text and path. Also add path as parameter to Parser functions


		//Choose Parser for json or txt
		List<CustomThread> parsedThreads = Arrays.asList(Parser.json());
//		List<CustomThread> parsedThreads = Parser.text();

		threadExecuter(parsedThreads);

		System.out.println("All threads have finished");
    }
	public static void threadExecuter(List<CustomThread> customThreads) throws InterruptedException {
		if (!customThreads.isEmpty()) {
			List<CustomThread> newList = new ArrayList<>();
			for (int i=0;i<customThreads.size();i++) {
				if (customThreads.get(i).getDependencyList().isEmpty()) {
					customThreads.get(i).start();
					customThreads.get(i).join();
					for (int j=0;j<customThreads.size();j++){
						customThreads.get(j).removeDependency(customThreads.get(i).getName());
					}
				}
				else{
					newList.add(customThreads.get(i));
				}
			}
			//Recursion "for the win!"
			threadExecuter(newList);
		}
	}
}
