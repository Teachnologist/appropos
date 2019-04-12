package com.apropos.crypto.app;

import com.apropos.classes.coinbaseProProducts;
import com.apropos.classes.coinbaseProducts;
import com.apropos.demoData.coinbaseProductsCache;
import com.apropos.demoThreads.coinbaseProductThread;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class webRouter {
    private String access_code = null;
    private static Integer init_site_counter = 0;

    @RequestMapping(value = "/")
    public String index(Model model){

        if(access_code != null){
            model.addAttribute("test","This is the authenticated page");
            return "authenticated/index";
        }

        coinbaseProducts.setProductCurrencyData();
        List products = coinbaseProducts.getProductCurrenciesData();

        if(init_site_counter < 1) {
            coinbaseProductsCache.setCurrency_product_data(products);
        }

        model.addAttribute("products", products);

        coinbaseProductThread rc = new coinbaseProductThread();
        Thread t = new Thread(rc);
        t.start();

        init_site_counter++;
        return "public/index";
    }
}
