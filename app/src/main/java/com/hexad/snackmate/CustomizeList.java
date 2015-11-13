package com.hexad.snackmate;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Chunhao Wu on 11/13/2015.
 */
public class CustomizeList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] name;
    private final Integer[] imageId;
    private final ImageLoader imageLoader;


    public CustomizeList(Activity context,
                      String[] name, Integer[] imageId, ImageLoader imageLoader) {
        super(context, R.layout.list_single, name);
        this.context = context;
        this.name = name;
        this.imageId = imageId;
        this.imageLoader = imageLoader;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single,null);
        TextView title = (TextView)rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        title.setText(name[position] );
        imageLoader.loadBitmap(imageId[position],imageView,150,150);
       // imageView.setImageResource(imageId[position]);

        return rowView;
    }

}
