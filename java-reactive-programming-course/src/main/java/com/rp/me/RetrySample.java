package com.rp.me;

import org.reactivestreams.Publisher;

import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

public class RetrySample {

	public static void main(String[] args) {
		Flux flux = Flux.just(4, 2);
		
		doSomething(flux);
	}
	
	public static <T> void doSomething(Flux<Integer> flux) {
	     flux.map(n -> n / 0)
	         .retryWhen(new Retry() {
	             @Override
	             public Publisher<?> generateCompanion(Flux<RetrySignal> retrySignals) {
	                 return retrySignals.map(rs -> getNumberOfTries(rs));
	             }
	         })
	         .subscribe();
	 }

	 private static Long getNumberOfTries(Retry.RetrySignal rs) {
	     if (rs.totalRetries() < 3) {
	    	 System.out.println("totalRetries : " + rs.totalRetries());
	         return rs.totalRetries();
	     } else {
	         System.err.println("retries exhausted");
	         throw Exceptions.propagate(rs.failure());
	     }
	 }

}
