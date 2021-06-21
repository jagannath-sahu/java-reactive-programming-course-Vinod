package com.rp.me;

import java.util.stream.Stream;

import reactor.core.publisher.Flux;

public class UsingBiFuncInReactive {

	public static void main(String[] args) {
		Flux<Integer> f1 = Flux.fromStream(Stream.of(1));

		Flux<String> f2 = f1.map(i ->{
		    throw new RuntimeException("bang");
		});

		f2.onErrorContinue((t, o) -> {
		    System.out.println("o = " + o.getClass());
		}).subscribe(System.out::println);

	}

}
