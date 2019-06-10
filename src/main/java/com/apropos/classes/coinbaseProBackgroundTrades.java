package com.apropos.classes;

import com.apropos.curl.publicAPI;
import com.apropos.objects.orderData;
import com.apropos.objects.tradeData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

public class coinbaseProBackgroundTrades {

    //https://www.goldencompassquant.com/2017/07/order-book-imbalance/
    private static String URL;
    private static Map<String,tradeData> latest_trade;
    private static Map<String,Integer> number_of_sells;
    private static Map<String,Integer> number_of_buys;
 /*   private static Map<String,orderData> smallest_ask;
    private static Map<String,orderData> largest_bid;

    private static Map<String,orderData> sell_wall;
    private static Map<String,orderData> buy_wall;

    private static Map<String,Float> ask_volume;
    private static Map<String,Float> buy_volume;

    private static Map<String,orderData> ask_list;
    private static Map<String,orderData> bid_list;
    */


    public static void setURL(String url) {
        URL = url;
    }

    private static void setLatestSell(String pair,tradeData trade){
        latest_trade.put(pair,trade);
    }

    private static void setNumber_of_sells(String pair,Integer count){number_of_sells.put(pair,count); }

    private static void setNumber_of_buys(String pair,Integer count){number_of_buys.put(pair,count); }


    private static void runOrderBookData(String pair) {
        JSONArray trades_data = getTradeData(pair);

        if (trades_data != null) {
            long latest_sell = 0;
            Integer count_of_sells = 0;
            Integer count_of_buys = 0;

            Date now = new Date();
            long epoch_start = now.getTime();
            long seconds_between_trades = 0;

            for (int i = 0; i < trades_data.length(); i++) {

                JSONObject obj = trades_data.getJSONObject(i);
                tradeData trade = new tradeData(obj.getString("time"), obj.getString("trade_id"), obj.getString("side"), obj.getString("price"), obj.getString("size"));
                seconds_between_trades += epoch_start - trade.getEpoch_seconds();
                epoch_start = trade.getEpoch_seconds();

                switch(trade.getSide()){
                    case "sell":
                        count_of_sells++;
                        if(latest_sell < trade.getEpoch_seconds()){
                            setLatestSell(pair,trade);
                            latest_sell = trade.getEpoch_seconds();
                        }
                            break;
                    case "buy":
                        count_of_buys++;
                            break;
                    default:
                        break;
                }
            }
            setNumber_of_buys(pair,count_of_buys);
            setNumber_of_sells(pair,count_of_sells);
        }
    }


    private static JSONArray getTradeData(String pair){
        JSONArray trades = null;
        try {
            String endpoint = "/products/"+pair+"/trades";
            publicAPI publicread = new publicAPI(URL, endpoint);
            trades = publicread.getprocallAPIArray();


        }catch(Exception e){
            System.out.println(e);
            trades = null;
        }
        return trades;
    }
}
