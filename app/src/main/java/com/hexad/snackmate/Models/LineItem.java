package com.hexad.snackmate.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Lun Li on 2015/11/14.
 * the item listed in the cart
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

    /**
     * LineItem constructor
     * @param snackItem
     * @param count
     */
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

    // getter methods

    /**
     * getCount
     * @return the amount of items
     * @throws Exception
     */
    public int getCount() throws Exception{ return fetchIfNeeded().getNumber(countKey).intValue();}

    /**
     * getTitle
     * @return the title of the item
     * @throws Exception
     */
    public String getTitle() throws Exception{
        return  fetchIfNeeded().getString(titleKey);
    }

    /**
     * getImage
     * @return the ImageURL
     * @throws Exception
     */
    public String getImageURL() throws Exception{
        return  fetchIfNeeded().getString(imageURLKey);
    }

    /**
     * getPrice
     * @return the price of the item
     * @throws Exception
     */
    public Double getPrice() throws Exception{
        return  fetchIfNeeded().getNumber(priceKey).doubleValue();
    }

    /**
     * getAverageRating
     * @return the rating
     * @throws Exception
     */
    public Double getAverageRating() throws Exception{
        return  fetchIfNeeded().getNumber(avgRatingKey).doubleValue();
    }

    /**
     * ParseQuery pull the data from parse
     * @return the data
     */
    static public ParseQuery<LineItem> getQuery(){
        return ParseQuery.getQuery(LineItem.class);
    }


}
