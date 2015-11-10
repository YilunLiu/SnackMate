package com.hexad.snackmate;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hexad.snackmate.Enumerations.SortType;
import com.hexad.snackmate.Items.SnackItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by yilunliu on 10/24/15.
 * ImageApadter for GridView
 * Loading Gridview with images
 */
public class ImageAdapter extends BaseAdapter {

    private static final String TAG = ImageAdapter.class.getSimpleName();
    private List<SnackItem> list;

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
        list = Global.list;
    }

    @Override
    public int getCount() {
        return list.size();
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

        final TextView textView = (TextView) cell.findViewById(R.id.item_cell_textView);
        final ImageView imageView = (ImageView) cell.findViewById(R.id.item_cell_imageView);
        SnackItem item = list.get(position);
        // set text
        String message = item.getTitle() + " $"
                + item.getPrice().toString();
        textView.setText(message);
        RatingBar ratingbar = (RatingBar)cell.findViewById(R.id.rating_bar);
        ratingbar.setRating(item.getAverageRating().floatValue());

        // configure the image view
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageLoader.loadBitmap(images[position], imageView, 250, 250);



        return cell;

    }

    public void SortByType(SortType sortType){

        switch(sortType){
            case Alphabetical:
                AlphabetComparator ac =new AlphabetComparator();
                Collections.sort(list, ac);
                break;
            case Price_high_to_low:
                PriceComparator pc =new PriceComparator();
                Collections.sort(list, pc);
                break;
            case Price_low_to_high:
                Collections.sort(list,new PriceComparator());
                Collections.reverse(list);
                break;
            case Rating:
                RatingComparator rc = new RatingComparator();
                Collections.sort(list, rc);
                break;
            default:
        }

        notifyDataSetChanged();

    }

}
