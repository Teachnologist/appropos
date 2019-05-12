package com.apropos.Threads;

import com.apropos.classes.coinbaseProBackgroundTasks1;

public class Background1 implements Runnable {

    public void run() {
        try {
            while(true) {
                System.out.println("background1 thread: " + Thread.currentThread().getName());
                coinbaseProBackgroundTasks1.start("https://api.coinbase.com/v2");
                Thread.sleep(15000);
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
}
