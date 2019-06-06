package com.apropos.objects;

public class orderData {

    private Float price;
    private Float size;
    private Integer count;
    private Float cumumlative_size;

    public Float getPrice() {
        return price;
    }

    public Float getSize() {
        return size;
    }

    public Integer getCount() {
        return count;
    }

    public Float getCumumlative_size() {
        return cumumlative_size;
    }

    public orderData(Float p, Float s, Integer c){
        price = p;
        size = s;
        count = c;
        cumumlative_size = s*c;
    }


}
