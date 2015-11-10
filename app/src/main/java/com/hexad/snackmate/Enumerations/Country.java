package com.hexad.snackmate.Enumerations;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yilunliu on 10/29/15.
 */

/* Enumeration on Country,
    int value is the country code
 */
public enum Country{
    China(86), Japan(81), North_Korea(850), Others(0), All(-1);

    public int country_code;

    private Country(int countryCode){
        this.country_code = countryCode;
    }

    private static final Map<Integer,Country> map = new HashMap<Integer,Country>();

    static {
        for (Country type : Country.values()) {
            map.put(type.country_code, type);
        }
    }

    public static Country fromInt(int i){
        Country type = map.get(Integer.valueOf(i));
        if (type == null){
            type = Country.Others;
        }
        return type;
    }

    public String toString(){
        switch (this){
            case All: return "All";
            case China: return "China";
            case Japan: return "Japan";
            case North_Korea: return "North Korea";
            case Others: return "Others";
            default: return "Others";
        }
    }
}