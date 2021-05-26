package com.rp.sec01;

import com.rp.courseutil.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Lec06AsyncSupplierRefactoring {

    public static void main(String[] args) {

        getName();

        getName()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(Util.onNext());

        getName();

        Util.sleepSeconds(10);
    }

    // Mono<T> doFinally(Consumer<SignalType> onFinally)
    // Mono<T> doAfterTerminate(Runnable afterTerminate)
    // Mono<T> doOnSubscribe(Consumer<? super Subscription> onSubscribe)
    private static Mono<String> getName(){
        System.out.println("entered getName method");
        return Mono.fromSupplier(() -> {
            System.out.println("Generating name..");
            Util.sleepSeconds(3);
            return Util.faker().name().fullName();
        }).map(String::toUpperCase)
          .doOnSubscribe(onSubscription -> System.out.println("Subscription Initiated"))
          .doFinally(onFinally -> System.out.println("finally block executed"))
          .doAfterTerminate(() -> System.out.println("Terminated"));
    }


}
