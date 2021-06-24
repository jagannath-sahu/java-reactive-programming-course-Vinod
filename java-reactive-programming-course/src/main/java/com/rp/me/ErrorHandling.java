package com.rp.me;

import reactor.core.publisher.Flux;

public class ErrorHandling {

	public static void main(String[] args) {
		
		Flux.range(0, 10).map(div -> 100/div)
	          .onErrorResume(e -> Flux.just(2, 3, 4, 5)) 
	      .subscribe(System.out::println);
		
		try {
			Thread.currentThread().sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
