package com.apropos.classes;

import com.apropos.curl.publicAPI;
import com.apropos.objects.orderData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class coinbaseProBackgroundOrderbook {

    //https://www.goldencompassquant.com/2017/07/order-book-imbalance/
    private static String URL;
    private static Map<String,orderData> smallest_ask;
    private static Map<String,orderData> largest_bid;

    private static Map<String,orderData> sell_wall;
    private static Map<String,orderData> buy_wall;

    private static Map<String,Float> ask_volume;
    private static Map<String,Float> buy_volume;

    private static Map<String,orderData> ask_list;
    private static Map<String,orderData> bid_list;


    public static void setURL(String url) {
        URL = url;
    }

    public static Float getSpread(String pair){
        orderData ask = getBestAsk(pair);
        orderData bid = getBestBid(pair);

        if(ask != null && bid != null){
            return ask.getPrice() - bid.getPrice();
        }
        return null;
    }

    public static orderData getSell_wall(String pair){
        System.out.println("PAIR: "+pair);
        System.out.print(sell_wall.toString());
        if(sell_wall.containsKey(pair)){
            return sell_wall.get(pair);
        }
        return null;
    }

    public static orderData getBuy_wall(String pair){
        System.out.println("PAIR: "+pair);
        System.out.print(buy_wall.toString());
        if(buy_wall.containsKey(pair)){
            return buy_wall.get(pair);
        }
        return null;
    }

    public static orderData getBestBid(String pair){
        System.out.println("PAIR: "+pair);
        System.out.print(largest_bid.toString());
        if(largest_bid.containsKey(pair)){
            return largest_bid.get(pair);
        }
        return null;
    }

    public static orderData getBestAsk(String pair){
        System.out.println("PAIR: "+pair);
        System.out.print(smallest_ask.toString());
        if(smallest_ask.containsKey(pair)){
            return smallest_ask.get(pair);
        }
        return null;
    }

    private static void setAsk_volume(String pair,Float ask){
        ask_volume.put(pair,ask);
    }


    private static void setSell_wall(String pair,orderData ask){
        sell_wall.put(pair,ask);
    }

    private static void setBuy_wall(String pair,orderData bid){
        buy_wall.put(pair,bid);
    }

    private static void setSmallest_ask(String pair,orderData ask){
        smallest_ask.put(pair,ask);
    }

    private static void setLargest_bid(String pair,orderData bid){
        largest_bid.put(pair,bid);
    }

    private static void runOrderBookData(String pair) {
        JSONObject order_book_data = getOrderbookData(pair, 2);

        if (order_book_data != null) {
            JSONArray asks = order_book_data.getJSONArray("asks");
            JSONArray bids = order_book_data.getJSONArray("bids");

            JSONArray best_ask = asks.getJSONArray(0);
            JSONArray best_bid = bids.getJSONArray(0);

            setSmallest_ask(pair,new orderData(Float.parseFloat(best_ask.get(0).toString()),Float.parseFloat(best_ask.get(1).toString()),Integer.parseInt(best_ask.get(2).toString())));
            setLargest_bid(pair,new orderData(Float.parseFloat(best_ask.get(0).toString()),Float.parseFloat(best_ask.get(1).toString()),Integer.parseInt(best_ask.get(2).toString())));

            Float largest_ask_size = 0f;
            Float largest_bid_size = 0f;



            for (int i = 0; i < asks.length(); i++) {
                JSONArray asks_data = asks.getJSONArray(i);
                orderData ask_od = new orderData(Float.parseFloat(asks_data.get(0).toString()),Float.parseFloat(asks_data.get(1).toString()),Integer.parseInt(asks_data.get(2).toString()));

                if(ask_od.getCumumlative_size() > largest_ask_size){
                    largest_ask_size = ask_od.getCumumlative_size();
                    setSell_wall(pair,ask_od);
                }

            }

            for (int q = 0; q < bids.length(); q++) {
                JSONArray bids_data = asks.getJSONArray(q);
                orderData bid_od = new orderData(Float.parseFloat(bids_data.get(0).toString()),Float.parseFloat(bids_data.get(1).toString()),Integer.parseInt(bids_data.get(2).toString()));

                if(bid_od.getCumumlative_size() > largest_bid_size){
                    largest_bid_size = bid_od.getCumumlative_size();
                    setBuy_wall(pair,bid_od);
                }
            }


        }
    }


    private static JSONObject getOrderbookData(String pair,Integer level){
        JSONObject rates = new JSONObject();
        try {
            String endpoint = "/products/"+pair+"/book?level="+level;
            publicAPI publicread = new publicAPI(URL, endpoint);
            JSONObject obj = publicread.getcallAPIObject();
            rates = obj;

        }catch(Exception e){
            System.out.println(e);
            rates = null;
        }
        return rates;

    }
}
