package com.hexad.snackmate;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
    private static LayoutInflater inflater=null;

    public ImageAdapter(Context c){
        context = c;
        imageLoader = new ImageLoader(c);
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        View cell;
        if (convertView != null){
            cell = convertView;
        } else {

            // create a new cell
            cell = inflater.inflate(R.layout.home_page_individual_item,null);
        }

        TextView textView = (TextView) cell.findViewById(R.id.item_cell_textView);
        ImageView imageView = (ImageView) cell.findViewById(R.id.item_cell_imageView);

        // set text
        textView.setText("Snack name ($5.00)");
        // configure the image view
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageLoader.loadBitmap(images[position],imageView,250,250);

        return cell;

    }
}
