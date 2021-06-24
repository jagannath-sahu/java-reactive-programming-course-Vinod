package com.rp.me;

import java.time.Duration;

import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

public class IntervalTest {

	public static void main(String[] args) {
		/**
		 * Create a Flux that emits long values starting with 0 and incrementing at
		 * specified time intervals on the global timer. 
		 */
		
		Flux.interval(Duration.ofMillis(200)).map(String::valueOf).subscribe(System.out::println);
		
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
