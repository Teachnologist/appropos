package com.apropos.crypto.app;

import com.apropos.classes.coinbasePairRelatedData;
import com.apropos.classes.coinbaseProProducts;
import com.apropos.classes.coinbaseProducts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

@Component
public class appInitialization {

    @Value("${coinbase.api.URL}")
    String URL;

    @Value("${coinbase.api.PROURL}")
    String PROURL;



    public Integer counter = 0;

    @EventListener
    public void ContextRefreshedEvent(ContextRefreshedEvent event) {
        System.out.println("\nAPPLICATION INITIALIZED COUNT: "+counter);
        coinbaseProProducts.setDefaults(PROURL,"USD");
        coinbaseProProducts.setProductList();
        coinbaseProProducts.setActiveproducts();
        coinbaseProducts.setDefault(URL);
        coinbasePairRelatedData.setURL(URL);
            counter++;

    }
}
