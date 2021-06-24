package com.rp.me;

import java.util.Arrays;
import java.util.List;

import com.rp.courseutil.Util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.SynchronousSink;

public class DoOnNext2 {

	public static void main(String[] args) {

		//live
		Flux.generate((SynchronousSink<Integer> synchronousSink) -> {
		      synchronousSink.next(1);
		    })
		.doOnNext(System.out::println)
		.doOnNext(number -> System.out.println(number + 4))
		.take(10)
		.subscribe(Util.subscriber());


		Flux<String> articlesFlux = Flux.create((FluxSink<String> sink) -> {
			/* get all the latest article from a server and emit them one by one to downstream. */
			// already created
			List<String> articals = getArticalsFromServer();
			articals.forEach(sink::next);
		});

		articlesFlux.subscribe(Util.subscriber());

	}

	public static List<String> getArticalsFromServer(){
		return Arrays.asList("Cognizant", "Jagannath");
	}

}
