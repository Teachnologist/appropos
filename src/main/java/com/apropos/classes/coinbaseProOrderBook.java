package com.apropos.classes;

import com.apropos.curl.publicAPI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class coinbaseProOrderBook {
    private static JSONObject ORDER_BOOK_JSON;
    private static JSONObject ORDER_BOOK_JSON_CHART;
    private static Map<String,Object> ORDER_BOOK_MAP;


    private static String PAIR = "USD-BTC";
    private static String MAPPED_PAIR = "BTC-USD";
    private static String URL;

    public static void setURL(String URL) {
        coinbaseProOrderBook.URL = URL;
    }

    public static void setPAIR(String PAIR) {
        coinbaseProOrderBook.PAIR = PAIR;
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

    public static Map<String, Object> getOrderBookMap() {
        return ORDER_BOOK_MAP;
    }

    public static JSONObject getOrderBookJson() {
        return ORDER_BOOK_JSON;
    }

    public static JSONObject getOrderBookJsonChart() {
        return ORDER_BOOK_JSON_CHART;
    }

    public static void setOrderBook(Integer level, Boolean include_json, Boolean include_chart, String asks_color, String bids_color){

        JSONObject order_book_data = getOrderBookAggregatedData(level);
        System.out.println("top of new data for level "+level+"\n");
        System.out.print(order_book_data);
        System.out.println("end of new data for level "+level+"\n");

        if(order_book_data != null) {

      /*  List<Map<String,String>> map_arr = new ArrayList<Map<String,String>>();
        JSONArray json_arr = new JSONArray();*/
            JSONArray asks = order_book_data.getJSONArray("asks");
            JSONArray bids = order_book_data.getJSONArray("bids");

            List<Map<String, String>> map_ask_array = new ArrayList<Map<String, String>>();
            List<Map<String, String>> map_bid_array = new ArrayList<Map<String, String>>();

            JSONArray json_ask_array = new JSONArray();
            JSONArray json_bid_array = new JSONArray();

            JSONArray CHART_LABEL_COLOR = new JSONArray();
            JSONArray CHART_LABEL_TYPE = new JSONArray();
            JSONArray CHART_LABEL_SIZE = new JSONArray();
            JSONArray CHART_LABEL_PRICE = new JSONArray();
            JSONArray CHART_LABEL_KEYS = new JSONArray();

            JSONArray chart_asks_types = new JSONArray();
            JSONArray chart_bid_types = new JSONArray();

            JSONArray chart_asks_colors = new JSONArray();
            JSONArray chart_bid_colors = new JSONArray();

            JSONArray chart_asks_prices = new JSONArray();
            JSONArray chart_bid_prices = new JSONArray();

            JSONArray chart_asks_sizes = new JSONArray();
            JSONArray chart_bid_sizes = new JSONArray();

            Float ask_size = 0.0f;
            Float ask_avg = 0.0f;
            Float ask_total = 0.0f;
            Float ask_count = 0.0f;

            Integer i_count = 0;

            Float bid_size = 0.0f;
            Float bid_avg = 0.0f;
            Float bid_total = 0.0f;
            Float bid_count = 0.0f;

            Integer b_count = 0;
            JSONArray ask_data = asks.getJSONArray(0);
            JSONArray bid_data = bids.getJSONArray(0);


            for (int i = 0; i < asks.length(); i++) {
                i_count++;
                JSONArray data = asks.getJSONArray(i);

                Map<String, String> map = new HashMap<String, String>();
                JSONObject json_obj = new JSONObject();


                ask_size += Float.parseFloat(data.get(1).toString());
                ask_count += Float.parseFloat(data.get(2).toString());
                Float a = Float.parseFloat(data.get(0).toString());
                Float b = Float.parseFloat(data.get(2).toString());

                Float at = a * b;
                ask_total += at;

                map.put("price", data.get(0).toString());
                map.put("size", data.get(1).toString());
                map.put("count", data.get(2).toString());
                map_ask_array.add(map);

                if (include_json) {
                    json_obj.put("price", data.get(0).toString());
                    json_obj.put("size", data.get(1).toString());
                    json_obj.put("count", data.get(2).toString());
                    json_ask_array.put(json_obj);

                }


                if(include_chart){
                    chart_asks_colors.put(asks_color);
                    chart_asks_types.put("asks");
                    chart_asks_prices.put(data.get(0).toString());
                    chart_asks_sizes.put(data.get(1).toString());

                }
            }

            CHART_LABEL_COLOR = chart_asks_colors;
            CHART_LABEL_TYPE = chart_asks_types;
            CHART_LABEL_PRICE = reverseJSONArray(chart_asks_prices);
            CHART_LABEL_SIZE = reverseJSONArray(chart_asks_sizes);

            ask_avg = ask_total / ask_count;


            for (int q = 0; q < bids.length(); q++) {
                JSONArray data = bids.getJSONArray(q);

                Map<String, String> map = new HashMap<String, String>();
                JSONObject json_obj = new JSONObject();

                bid_size += Float.parseFloat(data.get(1).toString());
                bid_count += Float.parseFloat(data.get(2).toString());

                Float bt = Float.parseFloat(data.get(0).toString()) * Float.parseFloat(data.get(2).toString());
                bid_total += bt;


                map.put("price", data.get(0).toString());
                map.put("size", data.get(1).toString());
                map.put("count", data.get(2).toString());
                map_bid_array.add(map);

                if (include_json) {
                    json_obj.put("price", data.get(0).toString());
                    json_obj.put("size", data.get(1).toString());
                    json_obj.put("count", data.get(2).toString());
                    json_bid_array.put(json_obj);
                }

                if(include_chart){
                    CHART_LABEL_COLOR.put(bids_color);
                    CHART_LABEL_TYPE.put("bids");
                    CHART_LABEL_PRICE.put(data.get(0).toString());
                    CHART_LABEL_SIZE.put(data.get(1).toString());

                }

            }

            bid_avg = bid_total / bid_count;

            Map<String, Object> askmap = new HashMap<String, Object>();
            askmap.put("asks", map_ask_array);
            askmap.put("bids", map_bid_array);

            JSONObject ask_json = new JSONObject();
            ask_json.put("asks", json_ask_array);
            ask_json.put("bids", json_bid_array);


            Map<String, Object> agg_map = new HashMap<String, Object>();
            JSONObject agg_json = new JSONObject();

            Float ask_percentage = ask_size / (ask_size + bid_size);
            Float ask_percentage_formatted = ask_percentage * 100;
            Float bid_percentage_formatted = 100 - ask_percentage_formatted;

            String bid_color = "green";

            if (bid_percentage_formatted > 40 && bid_percentage_formatted < 60) {
                bid_color = "orange";
            } else if (bid_percentage_formatted < 40) {
                bid_color = "red";
            }

            agg_map.put("ask_size", ask_size);
            agg_map.put("ask_count", ask_count);
            agg_map.put("ask_total", ask_total);
            agg_map.put("ask_avg", ask_avg);

            agg_map.put("bid_size", bid_size);
            agg_map.put("bid_count", bid_count);
            agg_map.put("bid_total", bid_total);
            agg_map.put("bid_avg", bid_avg);
            agg_map.put("bid_color", bid_color);
            agg_map.put("ask_percentage", ask_percentage);
            agg_map.put("ask_percentage_formatted", ask_percentage_formatted);
            agg_map.put("bid_percentage_formatted", bid_percentage_formatted);

            if (include_json) {
                agg_json.put("ask_size", ask_size);
                agg_json.put("ask_count", ask_count);
                agg_json.put("ask_total", ask_total);
                agg_json.put("ask_avg", ask_avg);
                agg_json.put("bid_size", bid_size);
                agg_json.put("bid_count", bid_count);
                agg_json.put("bid_total", bid_total);
                agg_json.put("bid_avg", bid_avg);
                agg_json.put("bid_color", bid_color);
                agg_json.put("ask_percentage", ask_percentage);
                agg_json.put("ask_percentage_formatted", ask_percentage_formatted);
                agg_json.put("bid_percentage_formatted", bid_percentage_formatted);
            }


            askmap.put("agg_map", agg_map);
            ask_json.put("agg_json", agg_json);






            JSONObject order_book_chart_json = new JSONObject();
            order_book_chart_json.put("CHART_LABEL_KEYS",CHART_LABEL_KEYS);
            order_book_chart_json.put("CHART_LABEL_COLOR",CHART_LABEL_COLOR);
            order_book_chart_json.put("CHART_LABEL_TYPE",CHART_LABEL_TYPE);
            order_book_chart_json.put("CHART_LABEL_SIZE",CHART_LABEL_SIZE);
            order_book_chart_json.put("CHART_LABEL_PRICE",CHART_LABEL_PRICE);

            System.out.print(agg_map);
            ORDER_BOOK_JSON = ask_json;
            ORDER_BOOK_MAP = askmap;
            ORDER_BOOK_JSON_CHART = order_book_chart_json;
        }else{
            ORDER_BOOK_JSON_CHART = null;
            ORDER_BOOK_JSON = null;
            ORDER_BOOK_MAP = null;

        }

    }

    private static JSONArray reverseJSONArray(JSONArray array){
        JSONArray new_array = new JSONArray();
        System.out.println("REVERSE: "+array.length());

        for(int i = array.length() - 1;i > -1;i--){


            new_array.put(array.get(i).toString());

        }
        return new_array;
    }

    private static JSONObject getOrderBookAggregatedData(Integer level){
        JSONObject rates = new JSONObject();
        try {
            String endpoint = "/products/"+PAIR+"/book?level="+level;
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
