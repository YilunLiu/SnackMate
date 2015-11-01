package com.hexad.snackmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ItemDetailActivity extends AppCompatActivity {
    private Integer[] images = {R.drawable.snack_pic_1,R.drawable.snack_pic_2,
            R.drawable.snack_pic_3,R.drawable.snack_pic_4,R.drawable.snack_pic_5,
            R.drawable.snack_pic_6,R.drawable.snack_pic_7,R.drawable.snack_pic_8,
            R.drawable.snack_pic_9,R.drawable.snack_pic_10,R.drawable.snack_pic_11,
            R.drawable.snack_pic_1,R.drawable.snack_pic_2,
            R.drawable.snack_pic_3,R.drawable.snack_pic_4,R.drawable.snack_pic_5,
            R.drawable.snack_pic_6,R.drawable.snack_pic_7,R.drawable.snack_pic_8,
            R.drawable.snack_pic_9,R.drawable.snack_pic_10,R.drawable.snack_pic_11};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent intent = getIntent();
        int id = intent.getIntExtra("ImgID", 0);
        ImageView imageView = (ImageView)findViewById(R.id.image_view);
        imageView.setImageResource(images[id]);



    }
}
