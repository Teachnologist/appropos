package com.apropos.demoData;


import java.util.List;

public class coinbaseGraphMath {

    public Float calculateSimpleMovingAverageOfPrice(List list_of_prices){
        Float price = 0f;
        Integer item_count = list_of_prices.size();
        for(int i = 0;i<item_count;i++){
            price += Float.parseFloat(list_of_prices.get(i).toString());

        }

        return price/item_count;
    }


}
