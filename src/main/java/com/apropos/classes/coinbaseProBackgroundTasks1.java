package com.apropos.classes;

import com.apropos.curl.publicAPI;
import com.apropos.objects.priceData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONObject;

import java.util.*;

public class coinbaseProBackgroundTasks1 {

    private static String URL;
    private static Map<String,priceData> PAIR_MAP = new HashMap<String,priceData>();

    public static void start(String url){
        runCurrencyData(url);
    }

    public static priceData getPriceDataByPair(String pair){
        System.out.println("PAIR: "+pair);
        System.out.print(PAIR_MAP.toString());
        if(PAIR_MAP.containsKey(pair)){
            return PAIR_MAP.get(pair);
        }
        return null;
    }


    public static void runCurrencyData(String url) {
URL = url;
        List base_currencies = coinbaseProProducts.getBaseCurrencies();
        List quote_currencies = coinbaseProProducts.getQuoteCurrencies();

        Collections.sort(base_currencies);
        Collections.sort(quote_currencies);

        List<Map<String, Object>> map_arr = new ArrayList<Map<String, Object>>();
        JSONArray json_arr = new JSONArray();

        System.out.print("base_currencies: " + base_currencies.toString() + "\n");
        System.out.print("quote_currencies: " + quote_currencies.toString() + "\n");

        Integer ordinal_index_key = 0;

        for (int i = 0; i < quote_currencies.size(); i++)
        {
            String q_currency = quote_currencies.get(i).toString().toUpperCase();
            if (!base_currencies.contains(q_currency)) {
                for (int q = 0; q < base_currencies.size(); q++) {
                    String uppercase_base = base_currencies.get(q).toString().toUpperCase();
                    JSONObject rates = getCurrencyRateData(uppercase_base);
                    ordinal_index_key++;

                    if (rates.has(q_currency)) {
                        String rate = rates.get(q_currency).toString();
                        setMap(q_currency,uppercase_base, Double.parseDouble(rate),Double.parseDouble(rate));
                    }

                }
            }
        }
    }

    private static void setMap(String fiat, String crypto, Double value, Double purchase_price){
        String combo = crypto+"-"+fiat;
        if(PAIR_MAP.containsKey(combo)){
           priceData price_data = PAIR_MAP.get(combo);
            List list_values = price_data.getPrice_list();
            Double pprice = price_data.getDemo_purchase_price();
            list_values.add(value);
            PAIR_MAP.put(combo,new priceData(list_values,value,pprice));
        }else{
            List list = new ArrayList();
            list.add(value);
            PAIR_MAP.put(combo,new priceData(list,value,purchase_price));
        }
    }

    private static JSONObject getCurrencyRateData(String quote){
        JSONObject rates = new JSONObject();
        try {
            String endpoint = "/exchange-rates?currency=" + quote;
            publicAPI publicread = new publicAPI(URL, endpoint);
            JSONObject obj = publicread.getcallAPIObject();
            JSONObject data = obj.getJSONObject("data");
            rates = data.getJSONObject("rates");
        }catch(Exception e){
            System.out.println(e);
        }
        return rates;
    }

}
