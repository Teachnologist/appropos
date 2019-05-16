package com.apropos.crypto.app;

import com.apropos.Threads.Background1;
import com.apropos.classes.*;
import com.apropos.demoData.coinbaseProductsCache;
import com.apropos.demoData.coinbasegraphPoints;
import com.apropos.demoThreads.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.apropos.demoData.coinbasegraphPoints.clearPriceGrowthLineGraphData;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Controller
public class webRouter {
    private String access_code = null;
    private static Integer init_site_counter = 0;
    private static List<Thread> THREADS = new ArrayList<Thread>();
    private static Thread active_1;
    private static String thread_name = null;


    public void background(){
        if(thread_name == null) {

            Background1 background = new Background1();
            active_1 = new Thread(background);
            active_1.start();
            thread_name = active_1.getName();
        }else{
            System.out.println("Thread "+thread_name+" is running");

        }
    }

    @RequestMapping(value = "/")
    public String index(Model model){
       // background();
        coinbaseProBackgroundTasks1.start("https://api.coinbase.com/v2");
        coinbaseProducts.setProductCurrencyData2();
        List products = coinbaseProducts.getProductCurrenciesData();
        model.addAttribute("products", products);
        init_site_counter++;
        return "public/index";
    }

    @RequestMapping(value = "/metrics/{pair}")
    public String metrics(Model model,@PathVariable(value = "pair", required=true) String pair){
        System.out.print("\nACTIVE THREADS: "+THREADS.toString());
        background();

        killAllThreads();
        coinbasegraphPoints.clearPriceGrowthLineGraphData();
        coinbasePairRelatedData.setPAIR(pair);
        coinbasePairRelatedData.callSellPrice();

        coinbasePricingThread cpgt = new coinbasePricingThread();
        Thread pricing_thread = new Thread(cpgt);
        pricing_thread.start();
        //THREADS.add(pricing_thread);
        System.out.println("THREAD 2: "+THREADS.toString());

        model.addAttribute("pair", pair);


        return "public/metrics";
    }

    @RequestMapping(value = "/trades/{pair}")
    public String trades(Model model,@PathVariable(value = "pair", required=true) String pair){
        background();
        coinbaseProTradeBook.setPAIR(pair);
        coinbaseProTradeBook.setTrades();
        model.addAttribute("pair", pair);
        model.addAttribute("data", coinbaseProTradeBook.getTradeBookMap());
        coinbaseTradeBookThread ctbt = new coinbaseTradeBookThread();
        Thread trade_thread = new Thread(ctbt);
        trade_thread.start();
        return "public/trades";
    }

    @RequestMapping(value = "/orders/{pair}")
    public String orders(Model model,@PathVariable(value = "pair", required=true) String pair){
        background();
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
        background();
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
