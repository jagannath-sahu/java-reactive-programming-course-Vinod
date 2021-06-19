package com.rp.sec08;

import java.time.Duration;
import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;

public class Assignment {
	
	public static double carPrice = 100;

	public static void main(String[] args) {	
		
		Flux.combineLatest(getMonthFlux(), getQuarterFlux(), (s, i) -> s * i )
        			.subscribe(Util.subscriber());
		
		try {
			Thread.currentThread().sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	

	}
	
	private static Flux<Double> getMonthFlux(){
        return Flux
				.interval(Duration.ZERO, Duration.ofSeconds(5))
				.map(tick -> {
					carPrice = carPrice + 100;
					return carPrice ;
				});
    }
	
	private static Flux<Double> getQuarterFlux(){
        return Flux
				.interval(Duration.ofSeconds(10))
				.map(tick -> {
					double min = 0.8;  
					double max = 1.2;   
					double randomNo = 2;
					System.out.println("random no : " + randomNo);
					return randomNo ;
				}).startWith(1d);
    }
	
//	Received : 200.0
//	Received : 300.0
//	Received : 400.0
//	random no : 2.0
//	Received : 800.0
//	Received : 1000.0       (400 + 100) * 2
//	Received : 1200.0       (500 + 100) * 2
//	random no : 2.0

}
