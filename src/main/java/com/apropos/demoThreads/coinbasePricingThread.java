package com.apropos.demoThreads;

import com.apropos.classes.coinbasePairRelatedData;

public class coinbasePricingThread implements Runnable {


    private static Boolean running = false;

    public void run() {

            try {
                while(true) {
                    System.out.println("pricing thread: " + Thread.currentThread().getName());
                    coinbasePairRelatedData.callSellPrice();
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("PRICING INTERRUPTED!");
                Thread.currentThread().interrupt(); // propagate interrupt
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
