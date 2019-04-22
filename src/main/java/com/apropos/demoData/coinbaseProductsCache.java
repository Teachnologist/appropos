package com.apropos.demoData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class coinbaseProductsCache {
    private static List<Map<String,Object>> currency_product_data;
    private static Map<String,Float> currency_data_mapped;

    public synchronized static void setCurrency_product_data(List<Map<String,Object>> currency_product_data) {
        coinbaseProductsCache.currency_product_data = currency_product_data;
        System.out.println("PRODUCTS SET: "+currency_product_data.toString());
        mapCurrencyValuesByProperty();
    }

    public synchronized static List<Map<String,Object>> getCurrency_product_data() {
        return currency_product_data;
    }

    public synchronized static Float getRateByPair(String pair){
        Float mapped = 0f;
        Float cost_value = 0f;

        if(currency_data_mapped != null){
            mapped = currency_data_mapped.get(pair);
            cost_value = mapped + (mapped*(0.0149f*3));
        }

        return cost_value;
    }

    public synchronized static Float getDifferenceOfRate(String pair,Float current_rate){
        return current_rate - getRateByPair(pair);
    }

    public synchronized static Float getPurchasePriceAtRuntime(Float current_rate){
        return current_rate + (current_rate*(0.0149f*3));
    }

    public synchronized static Float getDifferenceOfRateRUNTIME(Float current_rate){
        return current_rate - getPurchasePriceAtRuntime(current_rate);
    }

    private synchronized static void mapCurrencyValuesByProperty(){
        if(currency_product_data != null) {
            currency_data_mapped = new HashMap<String,Float>();

            for(int i=0;i<currency_product_data.size();i++){
                ArrayList original_list_by_property = (ArrayList) currency_product_data.get(i).get("values");

                for(int q=0;q<original_list_by_property.size();q++) {
                    Map<String, Object> props = (Map<String, Object>) original_list_by_property.get(q);
                    System.out.println("****oldp1****");
                    System.out.print(props.get("pair"));
                    System.out.println("****oldp2****");
                    String pair = props.get("pair").toString();
                    Float rate = Float.parseFloat(props.get("rate").toString());
                    currency_data_mapped.put(pair,rate);
                }

            }




        }


        System.out.println("CURRENCT MAPPED: "+currency_data_mapped.toString());

    }


}
