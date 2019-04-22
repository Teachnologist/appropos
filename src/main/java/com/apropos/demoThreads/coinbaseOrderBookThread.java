package com.apropos.demoThreads;

import com.apropos.classes.coinbaseProOrderBook;
import com.apropos.classes.coinbaseProducts;

public class coinbaseOrderBookThread implements Runnable {


    private static Boolean running = false;

    public void run() {

            try {
                while(true) {
                    System.out.println("currencies thread: " + Thread.currentThread().getName());
                    coinbaseProOrderBook.setOrderBook(2,true,true,"orange","blue");
                    System.out.println(coinbaseProOrderBook.getOrderBookJson());
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
