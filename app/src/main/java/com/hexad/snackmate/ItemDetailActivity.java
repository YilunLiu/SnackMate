package com.hexad.snackmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hexad.snackmate.Items.Global;
import com.hexad.snackmate.Items.SnackItem;

public class ItemDetailActivity extends AppCompatActivity {
    private Integer[] images = {R.drawable.snack_pic_1,R.drawable.snack_pic_2,
            R.drawable.snack_pic_3,R.drawable.snack_pic_4,R.drawable.snack_pic_5,
            R.drawable.snack_pic_6,R.drawable.snack_pic_7,R.drawable.snack_pic_8,
            R.drawable.snack_pic_9,R.drawable.snack_pic_10,R.drawable.snack_pic_11,
            R.drawable.snack_pic_1,R.drawable.snack_pic_2,
            R.drawable.snack_pic_3,R.drawable.snack_pic_4,R.drawable.snack_pic_5,
            R.drawable.snack_pic_6,R.drawable.snack_pic_7,R.drawable.snack_pic_8,
            R.drawable.snack_pic_9,R.drawable.snack_pic_10,R.drawable.snack_pic_11};
    private SnackItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent intent = getIntent();
        int id = intent.getIntExtra("itemid", 0);
        item = Global.list.get(id);
        ImageView imageView = (ImageView)findViewById(R.id.image_itemdetail_view);
        imageView.setImageResource(images[id]);
        TextView textView = (TextView)findViewById(R.id.title_itemdetail_view);
        String message = item.getTitle() + " $"
                + item.getPrice().toString() + "\n" + " Rating: " +
                item.getAverageRating().toString();
        textView.setText(message);



    }
}
