package com.hexad.snackmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hexad.snackmate.Items.LineItem;
import com.hexad.snackmate.Items.SnackItem;

import java.util.List;

/**
 * Created by Allen on 2015/11/14.
 */
public class CartItemAdapter extends ArrayAdapter<LineItem>{



    private int resourceId;
    private ImageLoader imageLoader;


    public CartItemAdapter(Context context, int textViewResourceId, List<LineItem> objects) {
        super(context, textViewResourceId, objects);
        imageLoader = new ImageLoader(context);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent){
        LineItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView img = (ImageView)view.findViewById(R.id.cart_item_img);
        TextView txt = (TextView)view.findViewById(R.id.cart_item_txt);
        RatingBar bar = (RatingBar)view.findViewById(R.id.cart_item_rating_bar);

        img.setImageResource(Global.images[position]);
        txt.setText(item.getTitle()+"*"+item.getCount());
        bar.setRating(item.getAverageRating().floatValue());
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageLoader.loadBitmap(Global.images[position], img, 250, 250);
        return view;


    }
}
