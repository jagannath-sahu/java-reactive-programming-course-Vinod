package com.rp.sec04.helper;

import com.rp.courseutil.Util;

public class PurchaseOrder {

    private String item;
    private String price;
    private int userId;

    public PurchaseOrder(int userId) {
        this.userId = userId;
        this.item = Util.faker().commerce().productName();
        this.price = Util.faker().commerce().price();
    }

    public String getItem() {
      return item;
    }

    public void setItem(String item) {
      this.item = item;
    }

    public String getPrice() {
      return price;
    }

    public void setPrice(String price) {
      this.price = price;
    }

    public int getUserId() {
      return userId;
    }

    public void setUserId(int userId) {
      this.userId = userId;
    }

    @Override
    public String toString() {
      return "PurchaseOrder [item=" + item + ", price=" + price + ", userId=" + userId + "]";
    }
}
