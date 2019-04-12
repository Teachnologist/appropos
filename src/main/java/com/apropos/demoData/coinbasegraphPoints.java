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
    private static Map<String,List> map_data = new HashMap<String,List>();

    private static JSONArray json_user_prices = new JSONArray();
    private static JSONArray json_current_prices = new JSONArray();
    private static JSONArray json_formatted_dates = new JSONArray();
    private static JSONObject json_data = new JSONObject();

    public synchronized static Map<String,List> getPriceGrowthLineGraphMap(){
        map_data.put("user_price",map_user_prices);
        map_data.put("current_price",map_current_prices);
        map_data.put("formatted_date",map_formatted_dates);
        return map_data;
    }

    public synchronized static JSONObject getPriceGrowthLineGraphJSON(){
        json_data.put("user_price",json_user_prices);
        json_data.put("current_price",json_current_prices);
        json_data.put("formatted_date",json_formatted_dates);
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


    private synchronized static void addMapObjectforPriceCompLineGraph(String user_price,String current_price,String formatted_date){

       map_user_prices.add(user_price);
       map_current_prices.add(current_price);
       map_formatted_dates.add(formatted_date);

    }

    private synchronized static void addJSONObjectforPriceCompLineGraph(String user_price,String current_price,String formatted_date){

        json_user_prices.put(user_price);
        json_current_prices.put(current_price);
        json_formatted_dates.put(formatted_date);

    }

    public synchronized static void addForPriceComparison(String user_price,String current_price,String formatted_date){
        System.out.println("ADD COMP: "+user_price+" "+current_price);
        addMapObjectforPriceCompLineGraph(user_price,current_price,formatted_date);
        addJSONObjectforPriceCompLineGraph(user_price,current_price,formatted_date);
    }




}
