package com.hexad.snackmate;

import com.hexad.snackmate.Items.SnackItem;
import com.hexad.snackmate.Items.SnackItemService;

import java.util.List;

/**
 * Created by Allen on 2015/11/3.
 * Global Variables Used.
 */
public class Global {
    public static List<SnackItem> list = SnackItemService.getAllSnackItemsSync();
    public static Integer[] images = {R.drawable.snack_pic_1,R.drawable.snack_pic_2,
            R.drawable.snack_pic_3,R.drawable.snack_pic_4,R.drawable.snack_pic_5,
            R.drawable.snack_pic_6};


}
