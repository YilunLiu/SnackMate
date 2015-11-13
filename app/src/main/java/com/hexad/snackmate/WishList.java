package com.hexad.snackmate;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by AxlWu on 2015/11/12.
 */
public class WishList extends Activity {

        protected void onCreate(Bundle SavedInstanceState ){
            super.onCreate(SavedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.wishlist);
    }
}
