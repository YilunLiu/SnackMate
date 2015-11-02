package com.hexad.snackmate;

import com.hexad.snackmate.Items.SnackItem;

import java.util.Comparator;

/**
 * Created by Michael on 2015/11/1.
 */
public class PriceComparator implements Comparator {
    public int compare(Object o1, Object o2){
        double price1 = ((SnackItem)o1).getPrice();
        double price2 = ((SnackItem)o2).getPrice();
        if (price1 < price2) return 1;
        if (price2 < price1) return  -1;
        return 0;
    }
}
