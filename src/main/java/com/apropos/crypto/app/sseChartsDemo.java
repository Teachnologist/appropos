package com.apropos.crypto.app;


import com.apropos.classes.coinbaseProOrderBook;
import com.apropos.demoData.coinbasegraphPoints;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/sse/demo",produces = "text/event-stream;charset=UTF-8")
public class sseChartsDemo {

    @RequestMapping(value="/charts",
            method= RequestMethod.GET,
            produces="text/event-stream;charset=UTF-8")
    public @ResponseBody
    String sendMessage(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Content-Type","text/event-stream;charset=UTF-8");

        try {
            Thread.sleep(5000);

            System.out.println("sse in 2");
            return "data: " + coinbasegraphPoints.getPriceGrowthLineGraphJSON() + "\n\n";
        }catch(Exception e){
            return "data: " +e.toString();
        }
    }

    @RequestMapping(value="/charts/orders",
            method= RequestMethod.GET,
            produces="text/event-stream;charset=UTF-8")
    public @ResponseBody
    String sendOrders(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Content-Type","text/event-stream;charset=UTF-8");

        try {
            Thread.sleep(5000);

            System.out.println("sse in 2");
            return "data: " + coinbaseProOrderBook.getOrderBookJsonChart() + "\n\n";
        }catch(Exception e){
            return "data: " +e.toString();
        }
    }



}
