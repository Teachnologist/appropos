package com.apropos.classes;

import com.apropos.curl.publicAPI;
import com.apropos.demoData.coinbasegraphPoints;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class coinbaseProTradeBook {

    private static JSONObject TRADE_BOOK_JSON;
    private static JSONObject TRADE_BOOK_JSON_CHART;
    private static Map<String,Object> TRADE_BOOK_MAP;


    private static String PAIR = "USD-BTC";
    private static String MAPPED_PAIR = "BTC-USD";
    private static String URL;

    public static void setURL(String URL) {
        coinbaseProTradeBook.URL = URL;
    }

    public static void setPAIR(String PAIR) {
        coinbaseProTradeBook.PAIR = PAIR;
        mapPair();
    }

    public static String getURL() {
        return URL;
    }

    public static String getPAIR() {
        return PAIR;
    }

    private static void mapPair(){

        String[] factors = PAIR.split("-");
        MAPPED_PAIR = factors[1]+"-"+factors[0];
    }

    private static void mapProduct(){

        String[] factors = PAIR.split("-");
        MAPPED_PAIR = factors[1];
    }

    public static JSONObject getTradeBookJson() {
        return TRADE_BOOK_JSON;
    }
    public static Map<String,Object> getTradeBookMap() {
        return TRADE_BOOK_MAP;
    }

    public static void setTrades(){
        System.out.println("TRADES CALLED");
        JSONArray trades_data = getTradeBookData();

        if(trades_data != null) {
            List<Map<String,String>> sells_list = new ArrayList<Map<String,String>>();
            List<Map<String,String>> buy_list = new ArrayList<Map<String,String>>();
            JSONArray sells_array = new JSONArray();
            JSONArray buy_array = new JSONArray();

            Integer count_of_sells = 0;
            Integer count_of_buys = 0;
            Float sell_volume = 0f;
            Float buy_volume = 0f;

            Boolean capture_last_transaction = false;

            for(int i=0;i<trades_data.length();i++){

                JSONObject obj = trades_data.getJSONObject(i);

            System.out.println("TEST: "+obj.toString());
                if(!capture_last_transaction) {
                    System.out.println("CAPTURING LAST....");
                    coinbasegraphPoints.setLastTradeData(obj.get("side").toString(),obj.get("time").toString(),obj.get("price").toString(),obj.get("size").toString());
                    capture_last_transaction = true;
                }

            switch(obj.get("side").toString()){
                case "sell":
                    sells_list.add(createMap("sell",obj.get("price").toString(),obj.get("time").toString(),obj.get("size").toString()));
                    sells_array.put(createJSONObject("sell",obj.get("price").toString(),obj.get("time").toString(),obj.get("size").toString()));
                    count_of_sells++;
                    sell_volume += Float.parseFloat(obj.get("size").toString());
                    break;
                case "buy":
                    buy_list.add(createMap("buy",obj.get("price").toString(),obj.get("time").toString(),obj.get("size").toString()));
                   buy_array.put(createJSONObject("buy",obj.get("price").toString(),obj.get("time").toString(),obj.get("size").toString()));
                    count_of_buys++;
                    sell_volume += Float.parseFloat(obj.get("size").toString());
                    break;
                default:
                    break;
            }

            }
            Integer sell_ratio_float = count_of_sells/(count_of_sells+count_of_buys);
            Float total_volume = sell_volume+buy_volume;
            Float sell_value_ratio = sell_volume/total_volume;

            JSONObject json_response = new JSONObject();
            json_response.put("count_of_sells",count_of_sells);
            json_response.put("count_of_buys",count_of_buys);
            json_response.put("sell_ratio_float",sell_ratio_float);
            json_response.put("total_volume",total_volume);
            json_response.put("sell_value_ratio",sell_value_ratio);
            json_response.put("sells",sells_array);
            json_response.put("buys",buy_array);
            json_response.put("latest_sell",sells_array.get(0));
            json_response.put("latest_buy",buy_array.get(0));

            Map<String,Object> map_response = new HashMap<String,Object>();
            map_response.put("count_of_sells",count_of_sells);
            map_response.put("count_of_buys",count_of_buys);
            map_response.put("sell_ratio_float",sell_ratio_float);
            map_response.put("total_volume",total_volume);
            map_response.put("sell_value_ratio",sell_value_ratio);
            map_response.put("sells",sells_list);
            map_response.put("buys",buy_list);
            map_response.put("latest_sell",sells_list.get(0));
            map_response.put("latest_buy",buy_list.get(0));



            TRADE_BOOK_JSON = json_response;
            TRADE_BOOK_MAP = map_response;
        }
    }

    private static Map<String,String> createMap(String side,String price,String time,String size){
        Map<String,String> map = new HashMap<String,String>();
        map.put("side",side);
        map.put("price",price);
        map.put("time",time);
        map.put("size",size);

return map;

    }

    private static JSONObject createJSONObject(String side,String price,String time,String size){
        JSONObject map = new JSONObject();
        map.put("side",side);
        map.put("price",price);
        map.put("time",time);
        map.put("size",size);
        return map;

    }

    private static JSONArray getTradeBookData(){
        JSONArray trades = null;
        try {
            String endpoint = "/products/"+PAIR+"/trades";
            publicAPI publicread = new publicAPI(URL, endpoint);
            trades = publicread.getprocallAPIArray();

        }catch(Exception e){
            System.out.println(e);
            trades = null;
        }
        return trades;
    }

}
