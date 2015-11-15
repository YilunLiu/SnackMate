package com.hexad.snackmate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hexad.snackmate.Items.LineItem;
import com.hexad.snackmate.Items.SnackItem;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lun Li on 10/30/15.
 * Show the item detail
 */
public class ItemDetailActivity extends AppCompatActivity {

    private SnackItem item;
    private ImageLoader imageLoader;
    private int counter = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (imageLoader == null){
            imageLoader = new ImageLoader(this);
        }

        setContentView(R.layout.activity_item_detail);

        Intent intent = getIntent();
        int id = intent.getIntExtra("itemid", 0);
        item = Global.list.get(id);
        RatingBar ratingbar = (RatingBar) findViewById(R.id.rating_bar);
        ratingbar.setRating(item.getAverageRating().floatValue());

        TextView textView = (TextView)findViewById(R.id.title_itemdetail_view);
        String message = item.getTitle() + " $"
                + item.getPrice().toString();
        textView.setText(message);

        int pictureWidth = (int) (this.getResources().getDisplayMetrics().widthPixels * 0.8);
        ImageView imageView = (ImageView) findViewById(R.id.image_itemdetail_view);
        imageLoader.loadBitmap(Global.images[id], imageView, pictureWidth, pictureWidth);

        Button wish = (Button)findViewById(R.id.item_detail_wish);
        wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser user = ParseUser.getCurrentUser();
                user.addUnique("wishList", item);
                user.saveInBackground();
                Toast.makeText(ItemDetailActivity.this, "Added to the Wishlist!", Toast.LENGTH_SHORT).show();
            }
        });

        Button cart = (Button)findViewById(R.id.item_detail_cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineItem lineItem = new LineItem(item, counter);
                ParseUser user = ParseUser.getCurrentUser();
                user.add("cart", lineItem);
                user.saveInBackground();
                Toast.makeText(ItemDetailActivity.this, "Added to the Cart!", Toast.LENGTH_SHORT).show();
            }
        });

        Button add = (Button)findViewById(R.id.item_add);
        Button subtract = (Button)findViewById(R.id.item_subtract);
        final TextView txt_counter = (TextView)findViewById(R.id.item_detail_counter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                txt_counter.setText(Integer.toString(counter));
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                if (counter < 0)
                    counter = 0;
                txt_counter.setText(Integer.toString(counter));
            }
        });
    }
}
