package com.apropos.objects;

import java.util.ArrayList;
import java.util.List;

public class priceData {
    List price_list = new ArrayList();
    Double price = 0.00;
    Double demo_purchase_price = 0.00;

    public priceData(List price_list,Double price){
        this.price = price;
        this.price_list = price_list;

    }

    public priceData(List price_list,Double price,Double demo_purchase_price){
        this.price = price;
        this.price_list = price_list;
        this.demo_purchase_price = demo_purchase_price;
    }

    public void setDemo_purchase_price(Double demo_purchase_price) {
        this.demo_purchase_price = demo_purchase_price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPrice_list(List price_list) {
        this.price_list = price_list;
    }

    public List getPrice_list() {
        return price_list;
    }

    public Double getDemo_purchase_price() {
        return demo_purchase_price;
    }

    public Double getPrice() {
        return price;
    }
}
