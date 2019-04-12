package com.apropos.demoThreads;

import com.apropos.classes.coinbaseProducts;

import java.util.Map;

public class coinbaseProductThread implements Runnable {


    private static Boolean running = false;

    public void run() {
        while(true) {
            try {
                System.out.println("currencies thread: " + Thread.currentThread().getName());
                coinbaseProducts.setProductCurrencyData();
                coinbaseProducts.converttoJSON();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
