package com.rp.sec04;

import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec07Timeout {

    public static void main(String[] args) {

        getOrderNumbers()
                .timeout(Duration.ofSeconds(2), fallback())
                .subscribe(Util.subscriber());

      // either can return a fallback value like above or can just log the final end statement
//        getOrderNumbers()
//                .timeout(Duration.ofSeconds(2))
//                .doFinally(onFinally -> {
//                    System.out.println("streaming ended");
//                }).subscribe(Util.subscriber());


        Util.sleepSeconds(60);

    }

    private static Flux<Integer> getOrderNumbers(){
        return Flux.range(1, 10)
                    .delayElements(Duration.ofSeconds(5));
    }

    private static Flux<Integer> fallback(){
        return Flux.range(100, 10)
                    .delayElements(Duration.ofSeconds(5));
    }

}
