package com.unipi.xa_gm;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class ThreadSimulation {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

		List<CustomThread> parsedThreads = null;

		if (args.length == 0)
		{
			System.out.println("Running with default model.json");

			//Choose Default Parser for json or txt
			parsedThreads = Arrays.asList(Parser.json());
//			parsedThreads = Parser.text();

		}
		else if (args.length == 1){
			for(String argument: args)
			{
				switch (argument)
				{
					case "json":
						parsedThreads = Arrays.asList(Parser.json());
						System.out.println("Running with model.json");
						break;
					case "text":
						parsedThreads = Parser.text();
						System.out.println("Running with p_precedence.txt and p_timings.txt");
						break;
					case "exit":
						System.exit(0);
						break;
					default:
						System.out.println("Please choose 'json' to execute for model.json or 'text' to choose 'p_precedence.txt' and 'p_timings.txt'");
						System.exit(1);
				}
			}
		}
		else{
			System.out.println("Unrecognized input, running with model.json'");
			parsedThreads = Arrays.asList(Parser.json());
			System.out.println("Running with default model.json");
		}


		System.out.println("-----APPLICATION START-----\n");

		System.out.println("-----Thread Simulation-----");
		//Main application
		threadExecuter(parsedThreads);
		System.out.println("All threads have finished");

		//Blockchain Creation with a prefix of 3 zeros.
		Blockchain blockchain = new Blockchain(parsedThreads,3);


		blockchain.sqlSave(blockchain.getBlockChain());

		//UnComment to print blockchain prettified
		blockchain.blockchainPrint();

		System.out.println("-----APPLICATION END-----");
    }

	public static void threadExecuter(List<CustomThread> customThreads) throws InterruptedException {
    	/**
		 * This method checks every thread of the given list
		 * and if it has no dependencies it begins execution ".start()"
		 * When it dies, it removes it from every other threads dependency list
		 * and from the given list.
		 * For every thread with dependencies, it adds it to a new list
		 * Finally it runs recursively for the new list
		 * When this method runs it ensures that every thread begins executing according
		 * to the parsed files (if possible)
		 * */
		if (!customThreads.isEmpty()) {
			List<CustomThread> noDepThreadsList = new ArrayList<>();
			List<CustomThread> newList = new ArrayList<>();
			for (int i=0;i<customThreads.size();i++) {
				if (customThreads.get(i).getDependencyList().isEmpty()) {
					noDepThreadsList.add(customThreads.get(i));
					customThreads.get(i).start();
				}
				else{newList.add(customThreads.get(i));}
			}
			for (int i=0;i<noDepThreadsList.size();i++) {
				noDepThreadsList.get(i).join();
				for (int j=0;j<customThreads.size();j++){
					customThreads.get(j).removeDependency(noDepThreadsList.get(i).getName());
				}
			}
			//Recursion "for the win!"
			threadExecuter(newList);
		}
	}
}
