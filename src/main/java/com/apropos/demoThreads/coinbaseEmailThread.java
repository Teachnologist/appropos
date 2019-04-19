package com.apropos.demoThreads;

import com.apropos.classes.coinbaseProducts;
import com.apropos.crypto.app.Email;
import com.apropos.demoData.coinbasegraphPoints;

public class coinbaseEmailThread implements Runnable {


    private static Boolean running = false;

    public void run() {

            try {
                Integer count = 0;
                while(true) {
                    System.out.println("currencies thread: " + Thread.currentThread().getName());
                    Email email = new Email();
                    email.setFromemail("jason_triche@rush.edu");
                    email.setContent(coinbasegraphPoints.getPriceGrowthLineGraphMap().toString());
                    email.sendEmail();
                    System.out.println("Email running "+count);
                    Thread.sleep(20000);
                    count++;
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
