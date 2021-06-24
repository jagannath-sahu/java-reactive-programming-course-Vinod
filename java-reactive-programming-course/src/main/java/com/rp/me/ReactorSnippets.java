package com.rp.me;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorSnippets {

	public static void main(String[] args) {

		countingLetters();

		findingMissingLetter();

		restoreMissingLetter();

	}

	private static List<String> words = Arrays.asList("the",
			"quick",
			"brown",
			"fox",
			"jumped",
			"over",
			"the",
			"lazy",
			"dog");


	public void simpleCreation() {
		Flux<String> fewWords = Flux.just("Hello", "World");
		Flux<String> manyWords = Flux.fromIterable(words);

		fewWords.subscribe(System.out::println);
		System.out.println();
		manyWords.subscribe(System.out::println);
	}


	public static void countingLetters() {
		Flux<String> manyLetters = Flux
				.fromIterable(words)
				.flatMap(word -> Flux.fromArray(word.split("")))
				.zipWith(Flux.range(1, Integer.MAX_VALUE),
						(string, count) -> String.format("%2d. %s", count, string));

		manyLetters.subscribe(System.out::println);
		manyLetters.count().subscribe(System.out::println);
	}


	// useful distinct method
	public static void findingMissingLetter() {
		Flux<String> manyLetters = Flux
				.fromIterable(words)
				.flatMap(word -> Flux.fromArray(word.split("")))
				.distinct()
				.sort()
				.zipWith(Flux.range(1, Integer.MAX_VALUE),
						(string, count) -> String.format("%2d. %s", count, string));

		manyLetters.subscribe(System.out::println);
	}


	public static void restoreMissingLetter() {
		Mono<String> missing = Mono.just("s");
		Flux<String> allLetters = Flux
				.fromIterable(words)
				.flatMap(word -> Flux.fromArray(word.split("")))
				.concatWith(missing)
				.distinct()
				.sort()
				.zipWith(Flux.range(1, Integer.MAX_VALUE),
						(string, count) -> String.format("%2d. %s", count, string));

		allLetters.subscribe(System.out::println);
	}


	public void shortCircuit() {
		Flux<String> helloPauseWorld = Mono.just("Hello")
		                                   .concatWith(Mono.just("world").delaySubscription(Duration.ofMillis(500)));
		helloPauseWorld.subscribe(System.out::println);
	}


	public void blocks() {
		Flux<String> helloPauseWorld = Mono.just("Hello")
		                                   .concatWith(Mono.just("world").delaySubscription(Duration.ofMillis(500)));
		helloPauseWorld.toStream()
		               .forEach(System.out::println);
	}


	public void firstEmitting() {
		Mono<String> a = Mono.just("oops I'm late")
		                     .delaySubscription(Duration.ofMillis(500));
		Flux<String> b = Flux.just("let's get", "the party", "started")
							.delaySubscription(Duration.ofMillis(400));

		Flux.concat(a, b)
		    .toIterable()
		    .forEach(System.out::println);
	}
}