package com.rp.sec01;

import java.util.function.Consumer;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class PubishOnvsSubscribeOn2 {

  public static void main(String[] args) {

    Consumer<Integer> consumer = s -> System.out.println(s + " : " + Thread.currentThread().getName());

    Flux.range(1, 5)
            .doOnNext(consumer)
            .map(i -> {
              System.out.println("Inside map the thread is " + Thread.currentThread().getName());
              return i * 10;
            })
            .publishOn(Schedulers.newElastic("First_PublishOn()_thread"))
            .doOnNext(consumer)
            .publishOn(Schedulers.newElastic("Second_PublishOn()_thread"))
            .doOnNext(consumer)
            .subscribeOn(Schedulers.newElastic("subscribeOn_thread"))
            .subscribe();

  }

}
