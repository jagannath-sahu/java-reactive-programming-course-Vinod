package com.rp.sec03;

import com.rp.courseutil.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxGenerate {

  public static void main(String[] args) {

    Flux.generate(synchronousSink -> {
      System.out.println("emitting");
      synchronousSink.next(Util.faker().country().name()); // only 1 time u can call next,if u will call next
                                                           // again then error

      // synchronousSink.error(new RuntimeException("oops"));
    }).take(2).subscribe(Util.subscriber());


  }

}
