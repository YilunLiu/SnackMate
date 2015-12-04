package com.hexad.snackmate.Models;

import com.hexad.snackmate.Enumerations.Country;
import com.hexad.snackmate.Enumerations.Taste;
import com.parse.ParseClassName;
import com.parse.ParseFile;
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
    public static String indexKey = "index";
    public static String titleKey = "title";
    public static String descriptionKey = "description";
    public static String imageKey = "image";
    public static String priceKey = "price";
    public static String countryKey = "country";
    public static String tasteKey = "taste";
    public static String avgRatingKey = "avgRating";
    public static String reviewsKey = "reviews";
    public static String objectIdKey = "objectId";

    /*default constructor*/
    public SnackItem(){

    }
    /* getter method */

    public int getIndex() {return getInt(indexKey); }


    public String getTitle(){
        return getString(titleKey);
    }

    public String getDescription(){
        return getString(descriptionKey);
    }

    public ParseFile getImage(){
        return getParseFile(imageKey);
    }

    public String getImageURL(){
        return getImage().getUrl();
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

    static public ParseQuery<SnackItem> getQuery(){
        return ParseQuery.getQuery(SnackItem.class);
    }
}

