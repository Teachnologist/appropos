package com.apropos.classes;

import com.apropos.curl.publicAPI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class coinbaseProProducts {

    private static List<String> product_pairs;
    private static List<Map<String,String>> currency_products;
    private static String URI;
    private static String CURRENCY;
    private static List BASE_CURRENCIES;
    private static List QUOTE_CURRENCIES;

    public synchronized static void setDefaults(String uri, String currency){
        coinbaseProProducts.URI = uri;
        coinbaseProProducts.CURRENCY = currency;
    }

    public synchronized static void setCURRENCY(String CURRENCY) {
        coinbaseProProducts.CURRENCY = CURRENCY;
    }

    public synchronized static void setURI(String URI) {
        coinbaseProProducts.URI = URI;
    }

    public synchronized static void setProduct_pairs(List<String> currency_pairs) {
        coinbaseProProducts.product_pairs = currency_pairs;
    }

    public synchronized static void setCurrency_products(List<Map<String, String>> currency_products) {
        coinbaseProProducts.currency_products = currency_products;
    }

    public synchronized static void setBaseCurrencies(List baseCurrencies) {
        coinbaseProProducts.BASE_CURRENCIES = baseCurrencies;
    }

    public synchronized static void setQuoteCurrencies(List quoteCurrencies) {
        coinbaseProProducts.QUOTE_CURRENCIES = quoteCurrencies;
    }

    public synchronized static List getQuoteCurrencies() {
        return coinbaseProProducts.QUOTE_CURRENCIES;
    }

    public synchronized static List getBaseCurrencies() {
        return coinbaseProProducts.BASE_CURRENCIES;
    }

    public synchronized static String getCURRENCY() {
        return coinbaseProProducts.CURRENCY;
    }


    public synchronized static String getURI() {
        return coinbaseProProducts.URI;
    }

    public synchronized static List<String> getProduct_pairs() {
        return coinbaseProProducts.product_pairs;
    }

    public synchronized static List<Map<String, String>> getCurrency_products() {
        return coinbaseProProducts.currency_products;
    }

    public static Boolean isProductPair(String pair){
        Boolean truthy = false;

        if(coinbaseProProducts.product_pairs.contains(pair)){
            truthy = true;
        }

        return truthy;
    }

    public static void setActiveproducts(){
        List<String> base_currencies = new ArrayList<String>();
        List<String> quote_currencies = new ArrayList<String>();
        //set endpoint
        publicAPI publicread = new publicAPI(coinbaseProProducts.URI,"/products");

        //get json from endpoint - in this case, it returns an array that does not have to be unwrapped
        JSONArray arr = publicread.getprocallAPIArray();


        for(int i=0;i<arr.length();i++){
            JSONObject obj = new JSONObject(arr.get(i).toString());

            String base = obj.get("base_currency").toString();
            String quote = obj.get("quote_currency").toString();

            if(base_currencies.indexOf(base) < 0){
                base_currencies.add(base);
            }

            if(quote_currencies.indexOf(quote) < 0){
                quote_currencies.add(quote);
            }

        }
        coinbaseProProducts.BASE_CURRENCIES = base_currencies;
        coinbaseProProducts.QUOTE_CURRENCIES = quote_currencies;
    }


    public static void setProductList(){

        List<Map<String,String>> listofobjects = new ArrayList<Map<String,String>>();
        List<String> listofpairs = new ArrayList<String>();
        //set endpoint
        publicAPI publicread = new publicAPI(coinbaseProProducts.URI,"/products");

        //get json from endpoint - in this case, it returns an array that does not have to be unwrapped
        JSONArray arr = publicread.getprocallAPIArray();


        for(int i=0;i<arr.length();i++){
            JSONObject obj = new JSONObject(arr.get(i).toString());


            Map<String, String> stringobject = new HashMap<String, String>();

            stringobject.put("pair", obj.get("id").toString());
            stringobject.put("id", obj.get("id").toString());

            stringobject.put("base_currency", obj.get("base_currency").toString());
            stringobject.put("quote_currency", obj.get("quote_currency").toString());
            stringobject.put("min_quote", obj.get("base_min_size").toString());
            stringobject.put("max_quote", obj.get("base_max_size").toString());

            if(!listofpairs.contains(obj.get("id").toString())) {
                listofpairs.add(obj.get("id").toString());
            }
            listofobjects.add(stringobject);
        }
        coinbaseProProducts.product_pairs = listofpairs;
        coinbaseProProducts.currency_products = listofobjects;
    }
}
