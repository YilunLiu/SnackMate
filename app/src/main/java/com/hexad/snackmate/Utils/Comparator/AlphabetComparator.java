package com.hexad.snackmate.Utils.Comparator;

import com.hexad.snackmate.Models.SnackItem;

import java.util.Comparator;

/**
 * Created by Michael on 2015/11/1.
 */
public class AlphabetComparator  implements Comparator {
    public int compare(Object o1, Object o2){
        String title1 = ((SnackItem)o1).getTitle();
        String title2 = ((SnackItem)o2).getTitle();
        return title1.compareToIgnoreCase(title2);
    }
}
