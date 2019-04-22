package com.apropos.crypto.app;

import com.apropos.classes.coinbasePairRelatedData;
import com.apropos.classes.coinbaseProOrderBook;
import com.apropos.classes.coinbaseProProducts;
import com.apropos.classes.coinbaseProducts;
import com.apropos.demoData.coinbaseProductsCache;
import com.apropos.demoData.coinbasegraphPoints;
import com.apropos.demoThreads.coinbaseEmailThread;
import com.apropos.demoThreads.coinbaseOrderBookThread;
import com.apropos.demoThreads.coinbasePricingThread;
import com.apropos.demoThreads.coinbaseProductThread;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static com.apropos.demoData.coinbasegraphPoints.clearPriceGrowthLineGraphData;

@Controller
public class webRouter {
    private String access_code = null;
    private static Integer init_site_counter = 0;
    private static List<Thread> THREADS = new ArrayList<Thread>();
    private static Thread active;

    @RequestMapping(value = "/")
    public String index(Model model){

        if(access_code != null){
            model.addAttribute("test","This is the authenticated page");
            return "authenticated/index";
        }

        coinbaseProducts.setProductCurrencyData(true);
        List products = coinbaseProducts.getProductCurrenciesData();

        if(init_site_counter < 1) {
            coinbaseProductsCache.setCurrency_product_data(products);

        }

        model.addAttribute("products", products);

        killAllThreads();
        coinbaseProductThread cpdt = new coinbaseProductThread();
        Thread product_thread = new Thread(cpdt);
        product_thread.start();
        THREADS.add(product_thread);
        System.out.println("THREAD 1: "+THREADS.toString());

        init_site_counter++;
        return "public/index";
    }

    @RequestMapping(value = "/metrics/{pair}")
    public String metrics(Model model,@PathVariable(value = "pair", required=true) String pair){
        System.out.print("\nACTIVE THREADS: "+THREADS.toString());

        killAllThreads();
        coinbasegraphPoints.clearPriceGrowthLineGraphData();
        coinbasePairRelatedData.setPAIR(pair);
        coinbasePairRelatedData.callSellPrice();

        coinbasePricingThread cpgt = new coinbasePricingThread();
        Thread pricing_thread = new Thread(cpgt);
        pricing_thread.start();
        THREADS.add(pricing_thread);
        System.out.println("THREAD 2: "+THREADS.toString());

        model.addAttribute("pair", pair);

        return "public/metrics";
    }

    @RequestMapping(value = "/trades/{pair}")
    public String trades(Model model,@PathVariable(value = "pair", required=true) String pair){
        model.addAttribute("pair", pair);

        return "public/trades";
    }

    @RequestMapping(value = "/orders/{pair}")
    public String orders(Model model,@PathVariable(value = "pair", required=true) String pair){

        coinbaseProOrderBook.setPAIR(pair);
        coinbaseProOrderBook.setOrderBook(2,true,true,"orange","blue");
        model.addAttribute("pair", pair);
        model.addAttribute("data", coinbaseProOrderBook.getOrderBookJson());


        killAllThreads();
        coinbaseOrderBookThread cobt = new coinbaseOrderBookThread();
        Thread order_thread = new Thread(cobt);
        order_thread.start();
        THREADS.add(order_thread);
        return "public/orders";
    }

    @RequestMapping(value = "/email/thread")
    public String backgroundemails(Model model){

        coinbasePairRelatedData.callSellPrice();

        coinbaseEmailThread cpgt = new coinbaseEmailThread();
        Thread pricing_thread = new Thread(cpgt);
        pricing_thread.start();
        THREADS.add(pricing_thread);
        System.out.println("THREAD 2: "+THREADS.toString());
        return "public/etest";


    }

    private void killAllThreads(){

        for(int i=0;i<THREADS.size();i++){
            System.out.println("interrupting threads 1");
            THREADS.get(i).interrupt();
            System.out.println("interrupting threads 2");
        }



    }

}
