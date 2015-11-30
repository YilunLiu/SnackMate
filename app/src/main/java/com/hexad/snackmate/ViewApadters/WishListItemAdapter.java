package com.hexad.snackmate.ViewApadters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hexad.snackmate.Models.SnackItem;
import com.hexad.snackmate.R;
import com.hexad.snackmate.Utils.Image.ImageLoader;

import java.util.List;

/**
 * Created by Chunhao Wu on 11/13/2015.
 */
public class WishListItemAdapter extends ArrayAdapter<SnackItem> {

    private int resourceId;
    private ImageLoader imageLoader;


    public WishListItemAdapter(Context context, int textViewResourceId, List<SnackItem> objects) {
        super(context, textViewResourceId, objects);
        imageLoader = new ImageLoader(context);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent){
        SnackItem item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView img = (ImageView)view.findViewById(R.id.wish_list_item_img);
        TextView txt = (TextView)view.findViewById(R.id.wish_list_item_txt);
        RatingBar bar = (RatingBar)view.findViewById(R.id.wish_list_item_rating_bar);

        txt.setText(item.getTitle());
        bar.setRating(item.getAverageRating().floatValue());
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageLoader.displayImage(item.getImageURL(),img);
        return view;


    }

}
