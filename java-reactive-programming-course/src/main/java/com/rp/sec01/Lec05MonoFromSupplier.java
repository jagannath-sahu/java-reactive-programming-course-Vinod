package com.rp.sec01;

import com.rp.courseutil.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class Lec05MonoFromSupplier {

    public static void main(String[] args) {

        // use just only when you have data already
        // Mono<String> mono = Mono.just(getName());

        // recomended way to use Mono i.e use supplier with Mono
        Supplier<String> stringSupplier = () -> getName();
        Mono<String> mono = Mono.fromSupplier(stringSupplier);
        mono.subscribe(
                Util.onNext()
        );

        Callable<String> stringCallable = () -> getName();
        Mono.fromCallable(stringCallable)
                .subscribe(
                        Util.onNext()
                );

        //here u will get different names
        Mono.fromCallable(stringCallable).repeat(4)
                  .subscribe(
                            Util.onNext()
                  );

        //here always u will get same name
        getName1().repeat(4).subscribe(Util.onNext());

        getName1().repeat(4).subscribeOn(Schedulers.boundedElastic()).subscribe(Util.onNext());
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        getName2().repeat(4).subscribe(Util.onNext());

    }

    private static String getName(){
        System.out.println("Generating name..");
        return Util.faker().name().fullName();
    }

    private static Mono<String> getName1() {
        System.out.println("Generating name...");
        return Mono.just(Util.faker().name().fullName());
    }

    private static Mono<String> getName2() {
      System.out.println("Generating name....");
      return Mono.just(Util.faker().name().fullName()).subscribeOn(Schedulers.boundedElastic());
    }

}
