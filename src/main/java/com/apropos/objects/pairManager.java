package com.apropos.objects;

public class pairManager {

    private String BASE = "";
    private String QUOTE = "";
    private String PAIR = "";
    private String IPAIR = "";

    public pairManager(String base_currency,String quote_currency){
        this.BASE = base_currency;
        this.QUOTE = quote_currency;
        this.PAIR = base_currency+"-"+quote_currency;
        this.IPAIR = quote_currency+"-"+base_currency;
    }

    public String getBASE() {
        return BASE;
    }

    public String getQUOTE() {
        return QUOTE;
    }

    public String getIPAIR() {
        return IPAIR;
    }

    public String getPAIR() {
        return PAIR;
    }
}
