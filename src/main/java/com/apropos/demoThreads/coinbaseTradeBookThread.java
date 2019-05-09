package com.apropos.demoThreads;

import com.apropos.classes.coinbaseProOrderBook;
import com.apropos.classes.coinbaseProTradeBook;

public class coinbaseTradeBookThread implements Runnable {


    private static Boolean running = false;

    public void run() {

            try {
                while(true) {
                    System.out.println("trades thread: " + Thread.currentThread().getName());
                    coinbaseProTradeBook.setTrades();
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("PRODUCTS INTERRUPTED!");
                    Thread.currentThread().interrupt(); // propagate interrupt
                System.out.println("PRODUCTS PROPOGATED!");
                return;

            }
        }

    public void startRunning(){
        setRunning(true);
    }

    public void stopRunning(){
        setRunning(false);
    }

    private static void setRunning(Boolean r){
        running = r;
    }

    private static Boolean getRunning(){
        return running;
    }



}
