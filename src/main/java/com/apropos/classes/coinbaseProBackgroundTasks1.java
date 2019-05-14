package com.apropos.classes;

import com.apropos.curl.publicAPI;
import com.apropos.objects.pairManager;
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

    public static Map<String,priceData> getPriceDataMap(){
            return PAIR_MAP;
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

        System.out.println("*NEW ALGORITHM*");

        for (int q = 0; q < base_currencies.size(); q++) {
            String uppercase_base = base_currencies.get(q).toString().toUpperCase();
            JSONObject currency_obj = getCurrencyRateData(uppercase_base);

            Iterator<String> currency_rates = currency_obj.keys();

            while(currency_rates.hasNext()) {
                String currency_rate = currency_rates.next();
           //     System.out.println("NEXT: "+currency_rate);

                if (currency_obj.get(currency_rate) != null &&
                        quote_currencies.contains(currency_rate) &&
                        currency_rate.toUpperCase() != uppercase_base) {

                    pairManager pair_manager = new pairManager(uppercase_base,currency_rate.toUpperCase());
                    String pair = pair_manager.getPAIR();
                    String inv_pair = pair_manager.getIPAIR();
                    String rate = currency_obj.get(currency_rate).toString();

                    setMap(pair_manager, Double.parseDouble(rate));


                    System.out.println(pair+" ### "+inv_pair+" !!! "+rate);
                    // do something with jsonObject here
                }
            }

   /*         System.out.println("#BASE: "+uppercase_base);

            if(rates1 != null) {

                System.out.print(rates1.toString());
            }else{
                System.out.print(uppercase_base+" is null");
            }*/
        }

        System.out.println("*LESS CALLS TO API!!!*");

      /*  for (int i = 0; i < quote_currencies.size(); i++)
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
        }*/
    }

    private static void setMap(pairManager pair_manager, Double value){
        String combo = pair_manager.getPAIR();
        if(PAIR_MAP.containsKey(combo)){
           priceData price_data = PAIR_MAP.get(combo);
            List list_values = price_data.getPrice_list();
            Double pprice = price_data.getDemo_purchase_price();
            list_values.add(value);
            PAIR_MAP.put(combo,new priceData(list_values,value,pprice));
        }else{
            List list = new ArrayList();
            list.add(value);
            PAIR_MAP.put(combo,new priceData(list,value,value));
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
