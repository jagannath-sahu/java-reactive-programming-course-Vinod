package com.rp.me;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ForcingParallelProcessing {

	public static void main(String[] args) {
		Flux.just("red", "white", "blue")
		  .log()
		  .flatMap(value ->
		     Mono.just(value.toUpperCase())
		       .subscribeOn(Schedulers.parallel()), //forcing to run on Parallel scheduler
		     2)
		.subscribe(value -> {
		  System.out.println("Consumed: " + value);
		});
		
		try {
			Thread.currentThread().sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
