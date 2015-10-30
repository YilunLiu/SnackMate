package com.hexad.snackmate.Items;

import com.hexad.snackmate.Enumerations.Country;
import com.hexad.snackmate.Enumerations.Taste;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by yilunliu on 10/29/15.
 */

/*
 Item structure: {
 	title: String,
	description: String,
	image: string (url),
	price: number,
	country: Int,
	taste: Int,
	avg rating: number;
	reviews: review (has-many relation)
	info updated: string (created by parse)
    }
 */

@ParseClassName("SnackItem")

public class SnackItem extends ParseObject {

    /* declear key for values in Parse Object */
    private static String titleKey = "title";
    private static String descriptionKey = "description";
    private static String imageURLKey = "imageURL";
    private static String priceKey = "price";
    private static String countryKey = "country";
    private static String tasteKey = "taste";
    private static String avgRatingKey = "avgRating";
    private static String reviewsKey = "reviews";


    /* getter method */
    public String getTitle(){
        return getString(titleKey);
    }

    public String getDescription(){
        return getString(descriptionKey);
    }

    public String getImageURL(){
        return getString(imageURLKey);
    }

    public Double getPrice(){
        return getNumber(priceKey).doubleValue();
    }

    public Country getCountry(){
        int countryCode = getNumber(countryKey).intValue();
        Country country = Country.fromInt(countryCode);
        return country;
    }

    public Taste getTaste(){
        int value = getNumber(tasteKey).intValue();
        Taste taste = Taste.fromInt(value);
        return taste;
    }

    public Double getAverageRating(){
        return getNumber(avgRatingKey).doubleValue();
    }

    //TODO getReviews


    static public ParseQuery<SnackItem> getQuery(){
        return ParseQuery.getQuery(SnackItem.class);
    }
}

