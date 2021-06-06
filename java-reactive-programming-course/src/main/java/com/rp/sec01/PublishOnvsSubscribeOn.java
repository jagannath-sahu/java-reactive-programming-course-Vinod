package com.rp.sec01;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class PublishOnvsSubscribeOn {

  public static void main(String[] args) {

    // https://www.vinsguru.com/reactor-schedulers-publishon-vs-subscribeon/

    Scheduler SchedulerElastic = Schedulers.boundedElastic();

    Flux<Integer> flux = Flux.range(0, 2)
        .map(i -> {
            System.out.println("Mapping one for " + i + " is done by thread " + Thread.currentThread().getName());
            return i;
        })
        .publishOn(SchedulerElastic)
        .map(i -> {
            System.out.println("Mapping two for " + i + " is done by thread " + Thread.currentThread().getName());
            return i;
        })
        .publishOn(Schedulers.parallel())
        .map(i -> {
            System.out.println("Mapping three for " + i + " is done by thread " + Thread.currentThread().getName());
            return i;
        });

      Runnable r = () -> flux
          .subscribeOn(Schedulers.single())
          .doOnCancel(() -> System.out.println("canceled"))
          .subscribe(s -> {
              System.out.println("Received " + s + " via " + Thread.currentThread().getName());
           });

      Thread t1 = new Thread(r, "t1");
      t1.start();

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
  }

}
