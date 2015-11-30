package com.hexad.snackmate.Utils.Comparator;

import com.hexad.snackmate.Models.SnackItem;

import java.util.Comparator;

/**
 * Created by Michael on 2015/11/1.
 */
public class RatingComparator implements Comparator {
    public int compare(Object o1, Object o2){
        double rate1 = ((SnackItem)o1).getAverageRating();
        double rate2 = ((SnackItem)o2).getAverageRating();
        if (rate1 < rate2) return 1;
        if (rate2 < rate1) return  -1;
        return 0;
    }
}
