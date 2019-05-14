package com.apropos.classes;

import com.apropos.curl.publicAPI;
import com.apropos.demoData.coinbaseProductsCache;
import com.apropos.demoData.coinbasegraphPoints;
import com.apropos.objects.priceData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import static com.apropos.classes.coinbaseProBackgroundTasks1.getPriceDataMap;

public class coinbaseProducts {

    private static String URL;

    private static List<Map<String,Object>> PRODUCT_CURRENCY_DATA_MAP;

    private static JSONArray PRODUCT_CURRENCY_DATA_JSON;

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
        return PRODUCT_CURRENCY_DATA_JSON;
    }

    public synchronized static Boolean isStreamReady(){
        if(PRODUCT_CURRENCY_DATA_JSON != null){
            return true;
        }

        return false;
    }

    public synchronized static void clearData(){
        PRODUCT_CURRENCY_DATA_MAP = null;
        PRODUCT_CURRENCY_DATA_JSON = null;
    }


    public synchronized static List<Map<String,Object>> getProductCurrenciesData(){
        return PRODUCT_CURRENCY_DATA_MAP;
    }

    public static void setProductCurrencyData2() {
        List base_currencies = coinbaseProProducts.getBaseCurrencies();
        List quote_currencies = coinbaseProProducts.getQuoteCurrencies();

        List<Map<String, String>> listofobject = new ArrayList<Map<String, String>>();
        JSONArray listofjson = new JSONArray();

        List<Map<String, Object>> map_arr = new ArrayList<Map<String, Object>>();
        Integer ordinal_index_key = 0;

        Map<String, priceData> price_map = coinbaseProBackgroundTasks1.getPriceDataMap();

        JSONArray json_arr = new JSONArray();


        JSONObject big_json_obj = new JSONObject();

        Map<String, Object> bigobject = new HashMap<String, Object>();


        Map<String, Map> storequotemap = new HashMap<String, Map>();
        Map<String, Map> storequotejason = new HashMap<String, Map>();

        JSONObject quote_json_obj = new JSONObject();
        Map<String, String> quoteobject = new HashMap<String, String>();


        for (Map.Entry<String, priceData> marker : price_map.entrySet()) {
            System.out.println("Key = " + marker.getKey() +
                    ", Value = " + marker.getValue().getDemo_purchase_price());

            String pair = marker.getKey();

            if(quoteobject.get("pair").toString().contains(pair.split("-")[1])){



            }else{
                //instantiate arraygi

            }

            /*quote object is short list of primary coinbase fiats, the second factor of pairManager;
            * View is arranged by this data*/
            String base_obj = pair.split("-")[0];
            String quote_obj = pair.split("-")[1];
            String inv_pair = quote_obj + " " + base_obj;


            Float runtime_demo_diff = Float.parseFloat(marker.getValue().getPrice().toString()) - Float.parseFloat(marker.getValue().getDemo_purchase_price().toString());
            Float runtime_demo_diff_percentage = runtime_demo_diff / Float.parseFloat(marker.getValue().getPrice().toString());
            Float druntime_diff_percentage = (runtime_demo_diff / Float.parseFloat(marker.getValue().getPrice().toString())) * 100;


            String diff_interaction = getCostDifferenceMessage(runtime_demo_diff);
            String druntime_interaction = getCostDifferenceMessage(runtime_demo_diff);


            quoteobject.put("quote", pair);
            quoteobject.put("rate", marker.getValue().getPrice().toString());
            quoteobject.put("pair", pair);
            quoteobject.put("invpair", inv_pair);
            quoteobject.put(base_obj, marker.getValue().getPrice().toString());
            quoteobject.put("runtime_rate", marker.getValue().getDemo_purchase_price().toString());
            quoteobject.put("runtime_demo_diff", runtime_demo_diff.toString());
            //     quoteobject.put("runtime_demo_diff_percentage", String.format("%.2f", "94.12345"));
            quoteobject.put("runtime_demo_diff_percentage", runtime_demo_diff_percentage.toString());
            quoteobject.put("runtime_diff_interaction", "runtime_diff_interaction");
            quoteobject.put("demo_diff", runtime_demo_diff.toString());
            quoteobject.put("demo_diff_percentage", String.format("%.2f", runtime_demo_diff_percentage));
            quoteobject.put("diff_interaction", diff_interaction);

            listofobject.add(quoteobject);

                            /*for mapping - traditional page render */
                             /*for json - ajax page render */

            if (true) {

                quote_json_obj.put("quote", quoteobject);
                quote_json_obj.put("rate", marker.getValue().getPrice().toString());
                quote_json_obj.put("pair", pair);
                quote_json_obj.put("invpair", inv_pair);

                quote_json_obj.put("validpair", pair);
                quote_json_obj.put(base_obj, marker.getValue().getPrice().toString());

                quote_json_obj.put("runtime_rate", marker.getValue().getDemo_purchase_price().toString());
                quote_json_obj.put("runtime_demo_diff", runtime_demo_diff.toString());
                quote_json_obj.put("runtime_demo_diff_percentage", String.format("%.2f", druntime_diff_percentage));
                quote_json_obj.put("runtime_diff_interaction", druntime_interaction);

                quote_json_obj.put("demo_diff", runtime_demo_diff.toString());
                quote_json_obj.put("demo_diff_percentage", String.format("%.2f", druntime_diff_percentage));
                quote_json_obj.put("diff_interaction", diff_interaction);
                listofjson.put(quote_json_obj);
            }


            String date = new Date().toString();
            bigobject.put("key", quote_obj);
            bigobject.put("date", date);
            bigobject.put("values", listofobject);
            bigobject.put("index", ordinal_index_key);

            map_arr.add(bigobject);

            if (true) {
                big_json_obj.put("key",quote_obj);
                big_json_obj.put("date", date);
                big_json_obj.put("values", listofjson);
                big_json_obj.put("index", ordinal_index_key);
                json_arr.put(big_json_obj);
            }
            ordinal_index_key++;

        }


        System.out.println("Complete..."+"\n");

                System.out.print("\n"+map_arr.toString()+"\n");
                System.out.print("\n"+json_arr.toString()+"\n");
                System.out.println("\n"+"....Complete"+"\n");
                PRODUCT_CURRENCY_DATA_MAP = map_arr;
                PRODUCT_CURRENCY_DATA_JSON = json_arr;

        PRODUCT_CURRENCY_DATA_MAP = map_arr;
        PRODUCT_CURRENCY_DATA_JSON = json_arr;



    }
    public static void setProductCurrencyData(Boolean include_json){

        List base_currencies = coinbaseProProducts.getBaseCurrencies();
        List quote_currencies = coinbaseProProducts.getQuoteCurrencies();

        Collections.sort(base_currencies);
        Collections.sort(quote_currencies);
        List<Map<String,Object>> map_arr = new ArrayList<Map<String,Object>>();
        JSONArray json_arr = new JSONArray();

        System.out.print("base_currencies: "+base_currencies.toString()+"\n");
        System.out.print("quote_currencies: "+quote_currencies.toString()+"\n");

        Integer ordinal_index_key = 0;

        for(int i = 0;i<quote_currencies.size();i++) {
            String q_currency = quote_currencies.get(i).toString().toUpperCase();
            if (!base_currencies.contains(q_currency)) {

/*for mapping - traditional page render */
                Map<String, Object> bigobject = new HashMap<String, Object>();
                 List<Map<String, String>> listofobject = new ArrayList<Map<String, String>>();
                 /*for mapping - traditional page render*/

                 /*for json - ajax page render */
                JSONArray listofjson = new JSONArray();
                JSONObject big_json_obj = new JSONObject();
                /*for json - ajax page render */

                for (int q = 0; q < base_currencies.size(); q++) {

                    Boolean found = false;

                    String uppercase_base = base_currencies.get(q).toString().toUpperCase();

                    JSONObject rates = coinbaseProducts.getCurrencyRateData(uppercase_base);

                      Map<String, String> quoteobject = new HashMap<String, String>();
                    JSONObject quote_json_obj = new JSONObject();

                    if (rates.has(q_currency)) {
                        String rate = rates.get(q_currency).toString();
                        if (rate != null) {

                            /*for mapping - traditional page render */
                            String pair = q_currency + "-" + uppercase_base;
                            String invpair = uppercase_base + "-" + q_currency;


                            String valid_pair = null;

                            if (coinbaseProProducts.isProductPair(pair)) {
                                valid_pair = pair;
                            } else if (coinbaseProProducts.isProductPair(invpair)) {
                                valid_pair = invpair;
                            }

                            System.out.println(pair+" PAIR: "+coinbaseProductsCache.getRateByPair(pair));

                            Float demo_original_rate = coinbaseProductsCache.getRateByPair(pair);

                            Float demo_diff = coinbaseProductsCache.getDifferenceOfRate(pair, Float.parseFloat(rate));

                            Float demo_diff_percentage = (demo_diff/demo_original_rate)*100;



                            Float druntime_original_rate = coinbaseProductsCache.getPurchasePriceAtRuntime(Float.parseFloat(rate));

                            Float druntime_diff = coinbaseProductsCache.getDifferenceOfRateRUNTIME(Float.parseFloat(rate));

                            Float druntime_diff_percentage = (druntime_diff/druntime_original_rate)*100;



                            String diff_interaction = getCostDifferenceMessage(demo_diff);
                            String druntime_interaction = getCostDifferenceMessage(druntime_diff);



                            quoteobject.put("quote", uppercase_base);
                            quoteobject.put("rate", rate);
                            quoteobject.put("pair", pair);
                            quoteobject.put("invpair", invpair);
                            quoteobject.put("validpair", valid_pair);
                            quoteobject.put(uppercase_base, rate);


                            quoteobject.put("runtime_rate", druntime_original_rate.toString());
                            quoteobject.put("runtime_demo_diff", druntime_diff.toString());
                            quoteobject.put("runtime_demo_diff_percentage", String.format("%.2f", druntime_diff_percentage));
                            quoteobject.put("runtime_diff_interaction", druntime_interaction);


                            quoteobject.put("demo_diff", demo_diff.toString());
                            quoteobject.put("demo_diff_percentage", String.format("%.2f", demo_diff_percentage));
                            quoteobject.put("diff_interaction", diff_interaction);

                            listofobject.add(quoteobject);
                            /*for mapping - traditional page render */
                             /*for json - ajax page render */

                             if(include_json) {
                                 quote_json_obj.put("quote", uppercase_base);
                                 quote_json_obj.put("rate", rate);
                                 quote_json_obj.put("pair", pair);
                                 quote_json_obj.put("invpair", invpair);

                                 quote_json_obj.put("validpair", valid_pair);
                                 quote_json_obj.put(uppercase_base, rate);

                                 quote_json_obj.put("runtime_rate", druntime_original_rate.toString());
                                 quote_json_obj.put("runtime_demo_diff", druntime_diff.toString());
                                 quote_json_obj.put("runtime_demo_diff_percentage", String.format("%.2f", druntime_diff_percentage));
                                 quote_json_obj.put("runtime_diff_interaction", druntime_interaction);

                                 quote_json_obj.put("demo_diff", demo_diff.toString());
                                 quote_json_obj.put("demo_diff_percentage", String.format("%.2f", demo_diff_percentage));
                                 quote_json_obj.put("diff_interaction", diff_interaction);
                                 listofjson.put(quote_json_obj);
                             }



                              /*for json - ajax page render */
                        }
                    }
                    //[{BTC:{USD:100,EUR:200,...    }]
                }
                String date = new Date().toString();
                bigobject.put("key", q_currency);
                bigobject.put("date", date);
                bigobject.put("values", listofobject);
                bigobject.put("index", ordinal_index_key);

                map_arr.add(bigobject);

                if(include_json) {
                    big_json_obj.put("key", q_currency);
                    big_json_obj.put("date", date);
                    big_json_obj.put("values", listofjson);
                    big_json_obj.put("index", ordinal_index_key);
                    json_arr.put(big_json_obj);
                }
                ordinal_index_key++;
            }
        }

        System.out.println("Complete..."+"\n");

        System.out.print("\n"+map_arr.toString()+"\n");
        System.out.print("\n"+json_arr.toString()+"\n");
        System.out.println("\n"+"....Complete"+"\n");
        PRODUCT_CURRENCY_DATA_MAP = map_arr;
        PRODUCT_CURRENCY_DATA_JSON = json_arr;
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

    private static String getCostDifferenceMessage(Float value){

        String diff_interaction = "EVEN";

        if(value < 0f){
            diff_interaction = "UNDER";
        }
        if(value > 0f){
            diff_interaction = "OVER";
        }

        return diff_interaction;
    }

  /*  public static void converttoJSON() {


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
    }*/
}
