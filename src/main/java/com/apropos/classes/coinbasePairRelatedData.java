package com.apropos.classes;

import com.apropos.curl.publicAPI;
import com.apropos.demoData.coinbaseProductsCache;
import com.apropos.demoData.coinbasegraphPoints;
import org.json.JSONObject;

import java.util.Date;

public class coinbasePairRelatedData {

    private static String PAIR = "USD-BTC";
    private static String MAPPED_PAIR = "BTC-USD";
    private static String URL;

    public static void setURL(String URL) {
        coinbasePairRelatedData.URL = URL;
    }

    public static void setPAIR(String PAIR) {
        coinbasePairRelatedData.PAIR = PAIR;
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



    public synchronized static void callSellPrice(){
        Float demo_original_rate = coinbaseProductsCache.getRateByPair(MAPPED_PAIR);
        Float f = 100.0f;
        String date = new Date().toString();
        JSONObject rates = getSellRateData();
        System.out.println("SELL RATE DATA: "+rates.toString()+" DEMO RATE: "+demo_original_rate.toString());

        if(rates.has("amount") && rates.has("currency")){



            coinbasegraphPoints.addForPriceComparison(demo_original_rate.toString(),rates.get("amount").toString(),date);
        }

    }

    private static JSONObject getSellRateData(){
        JSONObject rates = new JSONObject();
        try {

            String endpoint = "/prices/"+PAIR+"/sell";
            publicAPI publicread = new publicAPI(URL, endpoint);
            JSONObject obj = publicread.getcallAPIObject();
            rates = obj.getJSONObject("data");

        }catch(Exception e){
            System.out.println(e);
        }
        return rates;
    }


}

