package com.apropos.classes;

import com.apropos.curl.publicAPI;
import com.apropos.demoData.coinbaseProductsCache;
import com.apropos.demoData.coinbasegraphPoints;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class coinbaseProducts {

    private static String URL;

    private static List<Map<String,Object>> PRODUCT_CURRENCY_DATA;

    private static JSONArray PRODUCT_CURRENCY_JSON;

    public synchronized static void setDefault(String URL) {
        coinbaseProducts.URL = URL;
    }

    public synchronized static void setURL(String URL) {
        coinbaseProducts.URL = URL;
    }

    public synchronized static String getURL() {
        return URL;
    }

    public synchronized static JSONArray getProductCurrencyJson() {
        return PRODUCT_CURRENCY_JSON;
    }

    public synchronized static Boolean isStreamReady(){
        if(PRODUCT_CURRENCY_JSON != null){
            return true;
        }

        return false;
    }

    public synchronized static void clearData(){
        PRODUCT_CURRENCY_DATA = null;
        PRODUCT_CURRENCY_JSON = null;
    }



    public synchronized static List<Map<String,Object>> getProductCurrenciesData(){
        return PRODUCT_CURRENCY_DATA;
    }

    public static void setProductCurrencyData(){

        List base_currencies = coinbaseProProducts.getBaseCurrencies();
        List quote_currencies = coinbaseProProducts.getQuoteCurrencies();

        Collections.sort(base_currencies);
        Collections.sort(quote_currencies);
        List<Map<String,Object>> arr = new ArrayList<Map<String,Object>>();

        System.out.print("base_currencies: "+base_currencies.toString()+"\n");
        System.out.print("quote_currencies: "+quote_currencies.toString()+"\n");

        Integer ordinal_index_key = 0;

        for(int i = 0;i<quote_currencies.size();i++) {
            String q_currency = quote_currencies.get(i).toString().toUpperCase();
            if (!base_currencies.contains(q_currency)) {

                 List<Map<String, String>> listofobject = new ArrayList<Map<String, String>>();
                Map<String, Object> bigobject = new HashMap<String, Object>();
                for (int q = 0; q < base_currencies.size(); q++) {

                    Boolean found = false;

                    String uppercase_base = base_currencies.get(q).toString().toUpperCase();

                    JSONObject rates = coinbaseProducts.getCurrencyRateData(uppercase_base);

                      Map<String, String> quoteobject = new HashMap<String, String>();

                    if (rates.has(q_currency)) {
                        String rate = rates.get(q_currency).toString();
                        if (rate != null) {
                            quoteobject.put("quote", uppercase_base);
                            quoteobject.put("rate", rate);
                            String pair = q_currency + "-" + uppercase_base;
                            quoteobject.put("pair", pair);

                            String invpair = uppercase_base + "-" + q_currency;
                            quoteobject.put("invpair", invpair);

                            String valid_pair = null;

                            if (coinbaseProProducts.isProductPair(pair)) {

                                valid_pair = pair;
                            } else if (coinbaseProProducts.isProductPair(invpair)) {
                                valid_pair = invpair;
                            }

                            quoteobject.put("validpair", valid_pair);

                            quoteobject.put(uppercase_base, rate);
                            listofobject.add(quoteobject);
                        }
                    }
                    //[{BTC:{USD:100,EUR:200,...    }]
                }
                bigobject.put("key", q_currency);
                String date = new Date().toString();
                bigobject.put("date", date);
                bigobject.put("values", listofobject);
                bigobject.put("index", ordinal_index_key);
                arr.add(bigobject);
                ordinal_index_key++;
            }
        }

        System.out.println("Complete..."+"\n");

        System.out.print("\n"+arr.toString()+"\n");
        System.out.println("\n"+"....Complete"+"\n");
        PRODUCT_CURRENCY_DATA = arr;
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

    public static void converttoJSON() {


        JSONArray json_currencies = new JSONArray();

        for (int i = 0; i < PRODUCT_CURRENCY_DATA.size(); i++) {

            String date = (String) PRODUCT_CURRENCY_DATA.get(i).get("date");

            List values = (ArrayList) PRODUCT_CURRENCY_DATA.get(i).get("values");

            for (int q = 0; q < values.size(); q++) {
                JSONObject obj = new JSONObject();
                Map<String, Object> props = (Map<String, Object>) values.get(q);
                System.out.println("****oldp1****");
                System.out.print(props.get("pair"));
                System.out.println("****oldp2****");
                String pair = props.get("pair").toString();
                Float rate = Float.parseFloat(props.get("rate").toString());

                System.out.println(pair+" PAIR: "+coinbaseProductsCache.getRateByPair(pair));

                Float demo_original_rate = coinbaseProductsCache.getRateByPair(pair);

                Float demo_diff = coinbaseProductsCache.getDifferenceOfRate(pair, rate);

                Float demo_diff_percentage = (demo_diff/demo_original_rate)*100;


                String diff_interaction = "EVEN";

                if(demo_diff < 0f){
                    diff_interaction = "UNDER";
                }
                if(demo_diff > 0f){
                    diff_interaction = "OVER";
                }

                obj.put("date", date);
                obj.put("pair", pair);
                obj.put("rate", rate.toString());

                obj.put("demo_diff", demo_diff.toString());
                obj.put("demo_diff_percentage", String.format("%.2f", demo_diff_percentage));
                obj.put("diff_interaction", diff_interaction);
                json_currencies.put(obj);
            }
        }
        PRODUCT_CURRENCY_JSON = json_currencies;
    }
}
