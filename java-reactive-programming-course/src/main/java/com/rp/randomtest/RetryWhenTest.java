package com.rp.randomtest;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import com.rp.courseutil.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class RetryWhenTest {

    static Flux<Integer> justError = Flux.concat(Flux.just(1),
        Flux.error(new RuntimeException("forced failure 0")));

    static Flux<Integer> rangeError = Flux.concat(Flux.range(1, 2),
        Flux.error(new RuntimeException("forced failure 0")));

    public static void main(String[] args) {
      //dontRepeat();
      //predicateThrows();
      twoRetryNormal();
//      twoRetryNormal1();
//      twoRetryNormalSupplier();
    }

    //check by using true in filter, this will retry always if true
    public static void dontRepeat() {
      rangeError.log().retryWhen(Retry.indefinitely().filter(e -> false)).log()
            .subscribe(Util.subscriber());
    }

    public static void predicateThrows() {
      rangeError.log()
              .retryWhen(
                      Retry.indefinitely()
                           .filter(e -> {
                               throw new RuntimeException("forced failure");
                           })
              ).log()
              .subscribe(Util.subscriber());
    }

    public static void twoRetryNormal() {
      AtomicInteger i = new AtomicInteger();

             Flux
              .just("test", "test2", "test3").log()
              .doOnNext(d -> {
                  System.out.println("before i : " + i.get());
                  if (i.getAndIncrement() < 2) {
                      System.out.println("before throw : " + i.get());
                      throw new RuntimeException("test");
                  }
                  System.out.println("after i : " + i.get());
              }).log()
              .retryWhen(Retry.indefinitely().filter(e -> i.get() <= 2)).log()
              .subscribe(Util.subscriber());

      System.out.println(i.get());
    }

    public static void twoRetryNormal1() {
      AtomicInteger i = new AtomicInteger();

      Mono<Long> source = Flux
          .just("test", "test2", "test3").log()
          .doOnNext(d -> {
              if (i.getAndIncrement() < 2) {
                  throw new RuntimeException("test");
              }
          }).log()
          .retryWhen(Retry.indefinitely().filter(e -> i.get() <= 2)).log()
          .count();

      System.out.println(i.get());
      System.out.println(source.block());
      System.out.println(i.get());

    }

    public static void twoRetryNormalSupplier() {
      AtomicInteger i = new AtomicInteger();
      AtomicBoolean bool = new AtomicBoolean(true);

      Flux.defer(() -> {
          return Flux.defer(() -> Flux.just(i.incrementAndGet())).log()
                     .doOnNext(v -> {
                         if (v < 4) {
                             throw new RuntimeException("test");
                         }
                         else {
                             bool.set(false);
                         }
                     }).log()
                     .retryWhen(Retry.indefinitely().filter(countingPredicate(e -> bool.get(), 3)));
      }).log().subscribe(Util.subscriber());
    }

    static <O> Predicate<O> countingPredicate(Predicate<O> predicate, long max) {
      if (max == 0) {
          return predicate;
      }
      return new Predicate<O>() {
          long n;

          @Override
          public boolean test(O o) {
              return n++ < max && predicate.test(o);
          }
      };
    }
}
