package com.unipi.xa_gm;

import java.io.FileNotFoundException;


public class ThreadSimulation {

    public static void main(String[] args) throws FileNotFoundException {

    	//TODO add command line arguments for json or text and path. Also add path as parameter to Parser functions
/*		//TODO Import p_timings text, into this dictionary
		Hashtable<CustomThread, Integer> p_timings = new Hashtable<>();

		//TODO Import p_precedence text, into this dictionary
		Hashtable<CustomThread, List<CustomThread>> p_precedence = new Hashtable<>();*/


		//TODO Add Main Functionality
		CustomThread[] mm = Parser.json();
		for (CustomThread customThread : mm){
			customThread.start();
		}
		System.out.println("["+CustomThread.currentThread().getName()+"] All threads have finished");

    }
}
