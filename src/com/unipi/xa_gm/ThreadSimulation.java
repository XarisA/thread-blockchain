package com.unipi.xa_gm;

import java.util.Hashtable;
import java.util.List;


public class ThreadSimulation {

    public static void main(String[] args) throws InterruptedException {

		//TODO Import p_timings (json || text) , into this dictionary
		Hashtable<CustomThread, Integer> p_timings = new Hashtable<>();

		//TODO Import p_precedence (json || text) , into this dictionary
		Hashtable<CustomThread, List<CustomThread>> p_precedence = new Hashtable<>();


		//TODO Add Main Functionality
		// Remove the following tests
	    CustomThread[] ctLists = new CustomThread[5];
	    for (int i=0; i<ctLists.length; i++){
	    	ctLists[i]= new CustomThread.Builder()
					.name("P"+i) 			//Get this from p_precedence
					.process_time(3000)		//Get this from p_timings.txt
					.build();
	    	ctLists[i].start();
		}

		for (int i=0; i<ctLists.length; i++){
			ctLists[i].join();
		}

		System.out.println("["+CustomThread.currentThread().getName()+"] All threads have finished");
		// Tests end

    }
}
