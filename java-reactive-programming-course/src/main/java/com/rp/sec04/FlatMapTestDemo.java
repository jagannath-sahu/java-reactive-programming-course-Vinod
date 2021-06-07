package com.rp.sec04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rp.courseutil.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FlatMapTestDemo {

//   https:github.com/reactor/reactor-core/blob/main/reactor-core/src/test/java/reactor/core/publisher/FluxFlatMapTest.java
  public static void main(String[] args) {

    Flux.range(1, 10).flatMap(v -> Flux.range(v, 2)).subscribe(Util.subscriber());


    Flux.<Integer>error(new RuntimeException("forced failure"))
                  .flatMap(v -> Flux.just(v)).subscribe(Util.subscriber());

    Flux.just(1).flatMap(v -> Flux.error(new RuntimeException("forced failure"))).subscribe(Util.subscriber());

    Flux.range(1, 10).flatMap(v -> Flux.just(v, v + 1)).subscribe(Util.subscriber());

    Flux.range(1, 10).flatMap(v -> Flux.just((Integer)null)).subscribe(Util.subscriber());

    Flux.<Integer>empty().flatMap(v -> Flux.just(v)).subscribe(Util.subscriber());

    Flux.range(1, 1000).flatMap(v -> Flux.<Integer>empty()).subscribe(Util.subscriber());

    Flux.range(1, 10).flatMap(Flux::just).subscribe(Util.subscriber());

    Flux.range(1, 20)
                .flatMap(v -> v % 2 == 0 ? Flux.just(v) : Flux.fromIterable(Arrays.asList(v)))
                .subscribe(Util.subscriber());

    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
        list.add(i);
    }

//     3 times stream 1 to 100
    Flux.range(1, 3).flatMap(v -> Flux.fromIterable(list)).subscribe(Util.subscriber());

    Flux.range(1, 3).flatMap(v -> Flux.range(v, 10)).subscribe(Util.subscriber());

    Integer[] array = new Integer[100];
    Arrays.fill(array, 77);

    Flux.range(1, 100).flatMap(v -> Flux.fromArray(array)).subscribe(Util.subscriber());

    Flux.range(1, 3).flatMap(v -> Flux.range(1, 10).map(w -> w + 1)).subscribe(Util.subscriber());

    Flux.just(1, 2, 3).flatMap(Flux::just).subscribe(Util.subscriber());

      Flux.just(1, 2, 3)
          .flatMap(d -> Mono.fromCallable(() -> {
              throw new Exception("test");
           })).subscribe(Util.subscriber());

      Flux.just(1, 2, 3)
          .flatMap(f -> Flux.empty(), Integer.MAX_VALUE).subscribe(Util.subscriber());

      Flux.just(
          Flux.just(1, 2),
          Mono.<Integer>error(new Exception("test")),
          Flux.just(3, 4)).flatMapDelayError(f -> f, 4, 4).subscribe(Util.subscriber());


      Flux.just(1, 2)
          .hide()
          .flatMap(f -> {
              if(f == 1){
                  return Mono.error(new NullPointerException());
              }
              else {
                  return Mono.just(f);
              }
          })
          .onErrorContinue((a, b) -> System.out.println("error1")).subscribe(Util.subscriber());

  }

}
