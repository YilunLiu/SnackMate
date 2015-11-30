package com.hexad.snackmate.ViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hexad.snackmate.Models.LineItem;
import com.hexad.snackmate.R;
import com.hexad.snackmate.Utils.Image.ImageLoader;

import java.util.List;

/**
 * Created by Lun Li on 2015/11/14.
 */
public class CartItemAdapter extends ArrayAdapter<LineItem>{



    private int resourceId;
    private ImageLoader imageLoader;


    public CartItemAdapter(Context context, int textViewResourceId, List<LineItem> objects) {
        super(context, textViewResourceId, objects);
        imageLoader = new ImageLoader(context);
        resourceId = textViewResourceId;
    }

    /**
     * getView
     * @param position
     * @param v
     * @param parent
     * @return the itme view in the list
     */
    @Override
    public View getView(int position, View v, ViewGroup parent){
        LineItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView img = (ImageView)view.findViewById(R.id.cart_item_img);
        TextView txt = (TextView)view.findViewById(R.id.cart_item_txt);
        RatingBar bar = (RatingBar)view.findViewById(R.id.cart_item_rating_bar);

        try {
            txt.setText(item.getTitle() + "*" + item.getCount());
            bar.setRating(item.getAverageRating().floatValue());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageLoader.displayImage(item.getImageURL(), img);
        }
        catch(Exception e){
        }
        return view;


    }
}
