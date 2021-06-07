package com.rp.sec04;

import com.rp.courseutil.Util;
import com.rp.sec04.helper.Person;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Lec11SwitchOnFirst {

    public static void main(String[] args) {

      //signal.get().getAge() will execute only once(remember this) means condition check only for first released element
        getPerson()
                .switchOnFirst((signal, personFlux) -> {
                    System.out.println("inside switch-on-first");
                    return signal.isOnNext() && signal.get().getAge() > 20 ? personFlux : applyFilterMap().apply(personFlux);
                })
                // .filter(e -> false)
                .subscribe(Util.subscriber());

        //sourceSubscribedOnce();
    }

    public static Flux<Person> getPerson(){
        return Flux.range(1, 10)
                .map(i -> new Person());
    }

    public static Function<Flux<Person>, Flux<Person>> applyFilterMap(){
        return flux -> flux
                .filter(p -> p.getAge() > 20)
                .doOnNext(p -> p.setName(p.getName().toUpperCase()))
                .doOnDiscard(Person.class, p -> System.out.println("Not allowing : " + p));
    }

    public static void sourceSubscribedOnce() {
      AtomicInteger subCount = new AtomicInteger();
      Flux<Integer> source = Flux.range(1, 10)
                                 .hide()
                                 .doOnSubscribe(subscription -> subCount.incrementAndGet());

      // s.get() will execute once only so will get value in this case 1(important remember this)
      source.switchOnFirst((s, f) -> f.filter(v -> v % 2 == s.get())).subscribe(Util.subscriber());

      //subCount should be 1
      System.out.println(subCount);
    }

}
