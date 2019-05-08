package com.apropos.demoData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class coinbasegraphPoints {
    private static List map_user_prices = new ArrayList<>();
    private static List map_current_prices = new ArrayList<>();
    private static List map_formatted_dates = new ArrayList<>();
    private static List map_price_ma = new ArrayList<>();
    private static List map_price_ma_constant = new ArrayList<>();
    private static Map<String,List> map_data = new HashMap<String,List>();

    private static JSONArray json_user_prices = new JSONArray();
    private static JSONArray json_current_prices = new JSONArray();
    private static JSONArray json_formatted_dates = new JSONArray();
    private static JSONArray json_price_ma = new JSONArray();
    private static JSONArray json_price_ma_constant = new JSONArray();
    private static JSONObject json_data = new JSONObject();

    private static JSONArray buy_data = new JSONArray();
    private static JSONArray sell_data = new JSONArray();
    private static JSONArray ma_data = new JSONArray();


    public static JSONObject getLastTradeData(){
        JSONObject obj = new JSONObject();
        obj.put("buy_data",buy_data);
        obj.put("sell_data",sell_data);
        obj.put("ma_data",ma_data);
        return obj;
    }

    public static void setLastTradeData(String type,String date, String price, String size){
        coinbaseGraphMath gm = new coinbaseGraphMath();
        Float price_moving_average = gm.calculateSimpleMovingAverageOfPrice(map_current_prices);

        switch(type){
            case "buy":
                buy_data.put(createPriceDataObject(date,price,size));
                ma_data.put(createPriceDataObject(date,price_moving_average.toString(),"1"));
                break;
            case "sell":
                sell_data.put(createPriceDataObject(date,price,size));
                ma_data.put(createPriceDataObject(date,price_moving_average.toString(),"1"));
                break;
        }
    }

    private static JSONObject createPriceDataObject(String date, String price, String size){
        JSONObject obj =  new JSONObject();
        obj.put("x",date);
        obj.put("y",price);
        obj.put("r",size);

        return obj;
    }


    public synchronized static Map<String,List> getPriceGrowthLineGraphMap(){
        map_data.put("user_price",map_user_prices);
        map_data.put("current_price",map_current_prices);
        map_data.put("formatted_date",map_formatted_dates);
        map_data.put("price_moving_average",map_price_ma_constant);
        return map_data;
    }

    public synchronized static JSONObject getPriceGrowthLineGraphJSON(){
        json_data.put("user_price",json_user_prices);
        json_data.put("current_price",json_current_prices);
        json_data.put("formatted_date",json_formatted_dates);
        json_data.put("price_moving_average",json_price_ma_constant);
        return json_data;
    }

    public synchronized static void clearPriceGrowthLineGraphData(){
       map_user_prices = new ArrayList<>();
       map_current_prices = new ArrayList<>();
       map_formatted_dates = new ArrayList<>();
       map_data = new HashMap<String,List>();

       json_user_prices = new JSONArray();
       json_current_prices = new JSONArray();
       json_formatted_dates = new JSONArray();
       json_data = new JSONObject();
    }

    private synchronized static void addMapObjectforPriceCompLineGraph(String user_price,String current_price,String formatted_date,String price_moving_average){

       map_user_prices.add(user_price);
       map_current_prices.add(current_price);
       map_formatted_dates.add(formatted_date);
        map_price_ma.add(price_moving_average);
    }

    private synchronized static void addJSONObjectforPriceCompLineGraph(String user_price,String current_price,String formatted_date,String price_moving_average){

        json_user_prices.put(user_price);
        json_current_prices.put(current_price);
        json_formatted_dates.put(formatted_date);
        json_price_ma.put(price_moving_average);

    }

    public synchronized static void addForPriceComparison(String user_price,String current_price,String formatted_date){
        System.out.println("ADD COMP: "+user_price+" "+current_price);
        coinbaseGraphMath gm = new coinbaseGraphMath();
        Float price_moving_average = gm.calculateSimpleMovingAverageOfPrice(map_current_prices);
        addMapObjectforPriceCompLineGraph(user_price,current_price,formatted_date,price_moving_average.toString());
        addJSONObjectforPriceCompLineGraph(user_price,current_price,formatted_date,price_moving_average.toString());

        makeListOfMovingAverageConstants(price_moving_average.toString(),map_user_prices.size());
    }

    private synchronized static void makeListOfMovingAverageConstants(String value,Integer size){
        map_price_ma_constant = new ArrayList<>();
        json_price_ma_constant = new JSONArray();

        for(int i=0;i<size;i++){
            map_price_ma_constant.add(value);
            json_price_ma_constant.put(value);
        }
    }




}
