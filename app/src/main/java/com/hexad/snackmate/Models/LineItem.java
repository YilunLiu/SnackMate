package com.hexad.snackmate.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Allen on 2015/11/14.
 */

/*
 Item structure: {
    reference: String,
    count: number,
 	title: String,
	image: string (url),
	avg rating: number;
	price: number,
    }
 */
@ParseClassName("LineItem")

public class LineItem extends ParseObject {
    /* declear key for values in Parse Object */
    public static String referenceKey = "reference";
    public static String countKey = "count";
    public static String titleKey = "title";
    public static String imageURLKey = "imageURL";
    public static String priceKey = "price";
    public static String avgRatingKey = "avgRating";

    public LineItem(){
//        super("LineTtem");
    }
//    public LineItem(SnackItem snackItem){
//        super("LineItem");
//        String id = snackItem.getObjectId();
//        put(referenceKey, id);
//        put(titleKey, snackItem.getTitle());
//        put(imageURLKey, snackItem.getImageURL());
//        put(priceKey, snackItem.getPrice());
//        put(avgRatingKey, snackItem.getAverageRating());
//        saveInBackground();
//    }

    public LineItem(SnackItem snackItem, int count){
        super("LineItem");
        String id = snackItem.getObjectId();
        put(referenceKey, id);
        put(countKey, count);
        put(titleKey, snackItem.getTitle());
        put(imageURLKey, snackItem.getImageURL());
        put(priceKey, snackItem.getPrice());
        put(avgRatingKey, snackItem.getAverageRating());
        saveInBackground();
    }

    public int getCount() throws Exception{ return fetchIfNeeded().getNumber(countKey).intValue();}
    public String getTitle() throws Exception{
        return  fetchIfNeeded().getString(titleKey);
    }

    public String getImageURL() throws Exception{
        return  fetchIfNeeded().getString(imageURLKey);
    }

    public Double getPrice() throws Exception{
        return  fetchIfNeeded().getNumber(priceKey).doubleValue();
    }

    public Double getAverageRating() throws Exception{
        return  fetchIfNeeded().getNumber(avgRatingKey).doubleValue();
    }

    static public ParseQuery<LineItem> getQuery(){
        return ParseQuery.getQuery(LineItem.class);
    }


}