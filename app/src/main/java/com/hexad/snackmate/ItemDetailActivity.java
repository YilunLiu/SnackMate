package com.hexad.snackmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hexad.snackmate.Items.SnackItem;

/**
 * Created by Lun Li on 10/30/15.
 * Show the item detail
 */
public class ItemDetailActivity extends AppCompatActivity {

    private SnackItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent intent = getIntent();
        int id = intent.getIntExtra("itemid", 0);
        item = Global.list.get(id);
        RatingBar ratingbar = (RatingBar) findViewById(R.id.rating_bar);
        ratingbar.setRating(item.getAverageRating().floatValue());
        ImageView imageView = (ImageView)findViewById(R.id.image_itemdetail_view);
        imageView.setImageResource(Global.images[id]);
        TextView textView = (TextView)findViewById(R.id.title_itemdetail_view);
        String message = item.getTitle() + " $"
                + item.getPrice().toString() + "\n" + " Rating: " +
                item.getAverageRating().toString();
        textView.setText(message);



    }
}
