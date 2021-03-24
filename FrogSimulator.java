
package com.josephcagle.randomstuff;

/**
 *
 *  A simulation program for the "Frog Problem" (see https://www.youtube.com/watch?v=ZLTyX4zL2Fc).
 *
 */

public class FrogSimulator {

	public static void main(String[] args) {
		System.out.println("Please watch the video: https://www.youtube.com/watch?v=ZLTyX4zL2Fc");

		java.util.Random random = java.util.concurrent.ThreadLocalRandom.current();

		// in this program, treat the far river bank as a lily pad.
		int starting_pad_options = args.length > 0 ? Integer.parseInt(args[0]) : 10;   // use 10 if no cmd-line option is provided
		System.out.println("Starting simulation with " + starting_pad_options +
				   " places for the frog to jump to (including the lily pads and the far river bank).");
		java.util.List<Integer> jump_totals = new java.util.LinkedList<>();   // use this to calculate the avg num of jumps

		int frog_pos = 0,
		    jumps = 0,
		    pads_left = starting_pad_options,
		    jump_amount = 0;   // declare these so they don't keep getting re-declared every time we loop (later on)

		int iterations = args.length > 1 ? Integer.parseInt(args[1]) : 1_000;   // use 1000 if no cmd-line option is provided
		System.out.println("Doing the simulation " + iterations + " times ...");
		System.out.println("(Use `java FrogSimulator.java [pads] [num of times to simulate]` to set different numbers)");

		for (int i=0; i<iterations; i++) {   // do a bunch of simulations
			frog_pos = 0;
			jumps = 0;
			pads_left = starting_pad_options;

			while (pads_left > 0) {   // here's the actual "jump to the other side of the river" part
				jump_amount = random.nextInt(pads_left) + 1;
				frog_pos += jump_amount;
				pads_left -= jump_amount;
				jumps++;
			}
			
			//System.out.println("Frog got to the end after " + jumps + " jumps.");   // un-comment this at your peril.
			                                                                          //(this outputs one line per simulation/iteration.)
			jump_totals.add(jumps);   // add the total number of jumps for this round to the list
		}
		
		// now get the average number of jumps
		double average_jumps = jump_totals.stream()
			                          .mapToInt(num -> num)   // use autounboxing
						  .average()
						  .orElse(0.0);   // so we get a double instead of an OptionalDouble (the 0.0 never happens)
		double expected_result = 0;
		for (int i=1; i<=starting_pad_options; i++) expected_result += 1.0/i;   // the formula to get the absolute, theoretical average
		                                                                        // also, make sure not to use Java int division
		double difference = expected_result - average_jumps;
		System.out.println("Average jumps: " + average_jumps);
		System.out.println("Expected result (theoretical average): " + expected_result);
		System.out.println("Difference (expected - measured): " + difference);

		System.out.println("Done!");
	}

}

