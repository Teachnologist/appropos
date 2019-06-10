package com.apropos.objects;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class tradeData {

    private String utc_time;
    private String trade_id;
    private String side;
    private Long epoch_seconds;
    private Float price;
    private Float size;

    public tradeData(String time, String t_id, String sd, String rate, String si){
        utc_time = time;
        trade_id = t_id;
        side = sd;
        price = Float.parseFloat(rate);
        size = Float.parseFloat(si);
        DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        ZonedDateTime zdt  = ZonedDateTime.parse(time,dtf);
        epoch_seconds = zdt.toEpochSecond();
    }

    public Float getPrice() {
        return price;
    }

    public Float getSize() {
        return size;
    }

    public Long getEpoch_seconds() {
        return epoch_seconds;
    }

    public String getSide() {
        return side;
    }

    public String getTrade_id() {
        return trade_id;
    }

    public String getUtc_time() {
        return utc_time;
    }


}
