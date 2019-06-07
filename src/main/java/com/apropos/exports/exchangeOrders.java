package com.apropos.exports;

import com.apropos.classes.coinbaseProBackgroundTasks1;
import org.json.JSONArray;

import java.util.List;

public class exchangeOrders {

    private static JSONArray price_list;

    public exchangeOrders(String pair) {
        Double current_price = coinbaseProBackgroundTasks1.getCurrentPriceByPair(pair);


    }


    public static void insertPrice(Double price){

        price_list.put(price);

    }



}
