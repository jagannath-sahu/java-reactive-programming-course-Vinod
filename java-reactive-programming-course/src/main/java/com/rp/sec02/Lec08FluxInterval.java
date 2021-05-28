package com.rp.sec02;

import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Consumer;

public class Lec08FluxInterval {

    public static void main(String[] args) {

        Flux.interval(Duration.ofSeconds(1))
                .subscribe(onNext());

        //till main exit in 5 secs
        Util.sleepSeconds(10);

    }

    public static Consumer<Object> onNext(){
      return (o -> {
        //can do any repeat task here
        System.out.println("can do anything here");
        System.out.println("Received : " + o);
      });
  }

}
