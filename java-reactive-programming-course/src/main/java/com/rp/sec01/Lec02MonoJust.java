package com.rp.sec01;

import com.rp.courseutil.Util;

import reactor.core.publisher.Mono;

public class Lec02MonoJust {

    public static void main(String[] args) {

        // publisher
//        Mono<Integer> mono = Mono.just(1);
//
//        System.out.println(mono);
//
//        mono.subscribe(i -> System.out.println("Received : " + i));

        Mono<Integer> mono1 = Mono.just(1);

        Mono<Integer> mono2 = Mono.error(() -> new RuntimeException());

        Mono<Void> mono3 = Mono.when(mono1, mono2).doOnSubscribe((a) -> System.out.println("on subscription")).then();


        mono3.subscribe(Util.onNext(), Util.onError(), Util.onComplete());

    }

}
