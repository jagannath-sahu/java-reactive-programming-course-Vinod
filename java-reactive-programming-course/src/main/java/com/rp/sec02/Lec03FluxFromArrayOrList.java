package com.rp.sec02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rp.courseutil.Util;
import com.rp.sec04.helper.PurchaseOrder;

import reactor.core.publisher.Flux;

public class Lec03FluxFromArrayOrList {

    public static void main(String[] args) {

          List<String> strings = Arrays.asList("a", "b", "c");
          Flux.fromIterable(strings)
                .subscribe(Util.onNext());

          // will stream as array (not recomended)
//          Flux.just(strings).subscribe(Util.onNext());

          // will stream as single element (use this as real time scenario)
          //Flux.just(strings).flatMap(Flux::fromIterable).subscribe(Util.onNext());

//        Integer[] arr = { 2, 5, 7, 8};
//        Flux.fromArray(arr)
//                .subscribe(Util.onNext());

         // getUsers().subscribe(Util.onNext());

    }

    public static Flux<User> getUsers() {
      List<User> list1 = Arrays.asList(
          new User(1),
          new User(2),
          new User(3)
          );
      return Flux.just(list1).flatMap(Flux::fromIterable);
    }

}

class User {

  private int userId;
  private String name;

  public User(int userId) {
      this.userId = userId;
      this.name = Util.faker().name().fullName();
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", name=" + name + "]";
  }
}

