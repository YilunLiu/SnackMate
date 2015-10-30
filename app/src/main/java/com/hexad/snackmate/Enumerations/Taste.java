package com.hexad.snackmate.Enumerations;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yilunliu on 10/29/15.
 */
public enum Taste {

    Sweet(0), Sour(1), Spicy(2), Salty(3), Others(4);

    private int value;

    private Taste(int value){
        this.value = value;
    }

    private static final Map<Integer,Taste> map = new HashMap<Integer,Taste>();

    static {
        for (Taste type : Taste.values()) {
            map.put(type.value, type);
        }
    }

    public static Taste fromInt(int i){
        Taste type = map.get(Integer.valueOf(i));
        if (type == null){
            type = Taste.Others;
        }
        return type;
    }

    public String toString(){
        switch (this){
            case Sweet: return "Sweet";
            case Sour: return "Sour";
            case Spicy: return "Spicy";
            case Salty: return "Salty";
            case Others: return "Others";
            default:return "Others";
        }
    }
}
