package com.rp.sec09.assignment;

import com.rp.courseutil.Util;
import lombok.Data;
import lombok.ToString;

public class PurchaseOrder {

    private String item;
    private double price;
    private String category;

    public PurchaseOrder() {
        this.item = Util.faker().commerce().productName();
        this.price = Double.parseDouble(Util.faker().commerce().price());
        this.category = Util.faker().commerce().department();
    }

    public String getItem() {
      return item;
    }

    public void setItem(String item) {
      this.item = item;
    }

    public double getPrice() {
      return price;
    }

    public void setPrice(double price) {
      this.price = price;
    }

    public String getCategory() {
      return category;
    }

    public void setCategory(String category) {
      this.category = category;
    }

    @Override
    public String toString() {
      return "PurchaseOrder [item=" + item + ", price=" + price + ", category=" + category + "]";
    }

}
