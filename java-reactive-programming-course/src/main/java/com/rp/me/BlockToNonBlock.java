package com.rp.me;

import java.util.Date;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class BlockToNonBlock {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void delay() {
	    try {
	        Thread.sleep(2000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}
	
	public String blockingGetInfo(Integer input) {
	    delay();
	    return String.format("[%d] on thread [%s] at time [%s]",
	            input,
	            Thread.currentThread().getName(),
	            new Date());
	}
	
	public void threadBlocking() {
		  Flux.range(1,5)
		        .flatMap(a -> Mono.just(blockingGetInfo(a)))
		        .subscribe(System.out::println);
	}

	
	public void threadNonBlockingBySchedulers() {
	    Flux.range(1,5)
	        .subscribeOn(Schedulers.boundedElastic())
	        .flatMap(a -> Mono.just(blockingGetInfo(a)))
	        .subscribe(System.out::println);

	}

}
