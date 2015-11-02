package com.hexad.snackmate.Items;

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
}
