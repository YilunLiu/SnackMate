package com.hexad.snackmate;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by yilunliu on 10/24/15.
 * ImageApadter for GridView
 * Loading Gridview with images
 */
public class ImageAdapter extends BaseAdapter {

    private static final String TAG = ImageAdapter.class.getSimpleName();

    private Context context;
    private Integer[] images = {R.drawable.snack_pic_1,R.drawable.snack_pic_2,
            R.drawable.snack_pic_3,R.drawable.snack_pic_4,R.drawable.snack_pic_5,
            R.drawable.snack_pic_6,R.drawable.snack_pic_7,R.drawable.snack_pic_8,
            R.drawable.snack_pic_9,R.drawable.snack_pic_10,R.drawable.snack_pic_11,
            R.drawable.snack_pic_1,R.drawable.snack_pic_2,
            R.drawable.snack_pic_3,R.drawable.snack_pic_4,R.drawable.snack_pic_5,
            R.drawable.snack_pic_6,R.drawable.snack_pic_7,R.drawable.snack_pic_8,
            R.drawable.snack_pic_9,R.drawable.snack_pic_10,R.drawable.snack_pic_11};
    private ImageLoader imageLoader;

    public ImageAdapter(Context c){
        context = c;
        imageLoader = new ImageLoader(c);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(300,300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);

        } else {
            imageView = (ImageView) convertView;
        }

        imageLoader.loadBitmap(images[position],imageView,300,300);

//        Log.d(TAG,"Loading Image at Position "+position);
        return imageView;
    }
}
