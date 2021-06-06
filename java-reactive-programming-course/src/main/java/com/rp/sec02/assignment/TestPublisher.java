package com.rp.sec02.assignment;

import java.util.concurrent.atomic.AtomicBoolean;
import reactor.core.publisher.Flux;

public class TestPublisher {

  static AtomicBoolean cancelled;

  public static AtomicBoolean getCancelled() {
    return cancelled;
  }

  public static void setCancelled(AtomicBoolean cancelled) {
    TestPublisher.cancelled = cancelled;
  }

  /**
   * Returns true if the downstream cancelled the sequence.
   * @return true if the downstream cancelled the sequence
   */
  //boolean isCancelled();

  public static Flux<Integer> getData(){
    cancelled = new AtomicBoolean();
    Flux<Integer> source = Flux.create(e -> {
      e.next(1);
      cancelled.set(e.isCancelled());
      e.next(2);
      e.next(3);
      e.complete();

    });

    return source;
  }

}
