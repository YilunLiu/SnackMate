package com.hexad.snackmate.Items;

import com.hexad.snackmate.Enumerations.Country;
import com.hexad.snackmate.Enumerations.SortType;
import com.hexad.snackmate.Enumerations.Taste;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by yilunliu on 10/29/15.
 */
public class SnackItemService {

    static public void getAllSnackItems(FindCallback<SnackItem> callback){
        ParseQuery<SnackItem> query = SnackItem.getQuery();
    }

    /* get all items in the database
     * this is a synchronous method
     */
    static public List<SnackItem> getAllSnackItemsSync(){
        ParseQuery<SnackItem> query = SnackItem.getQuery();
        List<SnackItem> list = null;
        try {
            list = query.find();
        }
        catch (ParseException e){
            //TODO
        }
        return list;
    }

    /*
     * method to get get all items
     * this is a synchronous method
     */
    static public List<SnackItem> getAllItems(SortType sortType, Integer limit){
        return getSnackItems(Country.All, Taste.All, sortType, limit);
    }

    /*
     * method to get get all items by country type
     * this is a synchronous method
     */
    static public List<SnackItem> getSnackByCountry(Country country, SortType sortType, Integer limit){
        return getSnackItems(country, Taste.All, sortType, limit);
    }

    /*
     * method to get get all items by taste type
     * this is a synchronous method
     */
    static public List<SnackItem> getSnackByTaste(Taste taste, SortType sortType, Integer limit){
        return getSnackItems(Country.All, taste, sortType, limit);
    }


    /*
     * this is a private method to get items by country/ taste/ sortType and limit
     * this is a synchronous method
     */
    static private List<SnackItem> getSnackItems(Country country, Taste taste, SortType sortType, Integer limit){
        ParseQuery<SnackItem> query = SnackItem.getQuery();
        List<SnackItem> list = null;

        /* set country code when search */
        if (country != Country.All){
            query.whereEqualTo(SnackItem.countryKey, country.country_code);
        }

        /* set taste value when search */
        if (taste != Taste.All){
            query.whereEqualTo(SnackItem.tasteKey, taste.value);
        }

        if (limit != null || limit != 0){
            query.setLimit(limit);
        }

        switch(sortType){
            case Price_high_to_low:
                query.orderByDescending(SnackItem.priceKey);
            case Price_low_to_high:
                query.orderByAscending(SnackItem.priceKey);
            case Rating:
                query.orderByDescending(SnackItem.avgRatingKey);
            case Alphabetical:
                query.orderByAscending(SnackItem.titleKey);
            default:
                query.orderByAscending(SnackItem.titleKey);
        }

        try{
            list = query.find();
        } catch (ParseException e ){
            //TODO
        }
        return list;
    }

}
