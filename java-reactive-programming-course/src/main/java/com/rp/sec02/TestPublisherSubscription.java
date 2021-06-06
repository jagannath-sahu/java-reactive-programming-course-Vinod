package com.rp.sec02;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import com.rp.sec02.assignment.TestPublisher;

public class TestPublisherSubscription {

  public static void main(String[] args) {
    CountDownLatch latch = new CountDownLatch(1);
    AtomicBoolean completed = new AtomicBoolean();
    AtomicBoolean errored = new AtomicBoolean();
    AtomicInteger i = new AtomicInteger();

    TestPublisher.getData()
              .subscribeWith(new Subscriber<Integer>() {

              private Subscription subscription;

              @Override
              public void onSubscribe(Subscription subscription) {
                  this.subscription = subscription;
                  subscription.request(Long.MAX_VALUE);
                  latch.countDown();
              }

              @Override
              public void onNext(Integer integer) {
                  i.incrementAndGet();
                  System.out.println(i);
                  latch.countDown();
                  throw new RuntimeException();
              }

              @Override
              public void onError(Throwable t) {
                  errored.set(true);
                  System.out.println("errored------" + errored);
                  latch.countDown();
              }

              @Override
              public void onComplete() {
                  completed.set(true);
                  System.out.println("completed---" + completed);
                  latch.countDown();
              }
            });

    System.out.println("complete------" + completed);
    System.out.println("cancel------" + TestPublisher.getCancelled());
    System.out.println("error------" + errored);

    try {
      latch.await();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
