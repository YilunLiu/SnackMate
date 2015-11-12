package com.hexad.snackmate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by AxlWu on 2015/11/12.
 */
public class WishList extends Activity {

        private ImageView appImageView;
        @Override
        protected void onCreate(Bundle SavedInstanceState ){
            super.onCreate(SavedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.wishlist);

            appImageView = (ImageView) findViewById(R.id.pic);
            appImageView.setImageResource(Global.images[0]);

    }
}
