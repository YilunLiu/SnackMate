package com.hexad.snackmate;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hexad.snackmate.Enumerations.Country;
import com.hexad.snackmate.Enumerations.SortType;
import com.hexad.snackmate.Enumerations.Taste;
import com.hexad.snackmate.Items.SnackItem;
import com.hexad.snackmate.Items.SnackItemService;
import com.hexad.snackmate.Utils.ImageLoader;

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
    private ImageLoader imageLoader;
    private static LayoutInflater inflater=null;
    private SortType sortType = SortType.Default;
    private Country country = Country.All;
    private Taste taste = Taste.All;

    public ImageAdapter(Context c, List<SnackItem> list){
        context = c;
        imageLoader = new ImageLoader(c);
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    public void setList(List<SnackItem> list) {
        this.list = list;
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
        final TextView priceView = (TextView) cell.findViewById(R.id.item_cell_textView_price);
        SnackItem item = list.get(position);
        // set text
        String message = item.getTitle();
        textView.setText(message);
        String price = "$ " + item.getPrice().toString();
        priceView.setText(price);
        RatingBar ratingbar = (RatingBar)cell.findViewById(R.id.rating_bar);
        ratingbar.setRating(item.getAverageRating().floatValue());

        // configure the image view
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageLoader.displayImage(item.getImageURL(),imageView);


        return cell;

    }

    public void SortByType(SortType sortType){
        if ((country.equals(Country.All) )&& (taste.equals(Taste.All))) {
            switch (sortType) {
                case Alphabetical:
                    AlphabetComparator ac = new AlphabetComparator();
                    Collections.sort(list, ac);
                    break;
                case Price_high_to_low:
                    PriceComparator pc = new PriceComparator();
                    Collections.sort(list, pc);
                    break;
                case Price_low_to_high:
                    Collections.sort(list, new PriceComparator());
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
        else if (country != Country.All){
            filterByCountry(sortType, country);
        }
        else{
            filterByTaste(sortType, taste);
        }

    }
    public void filterByCountry(Country country){
        filterByCountry(sortType,country);
    }

    public void filterByTaste(Taste taste){
        filterByTaste(sortType,taste);
    }

    private void filterByCountry(SortType sortType, Country country){
        if (country == Country.All)
            this.country = country;
        else{
            list = SnackItemService.getSnackByCountry(country, sortType, 10);
        }
        notifyDataSetChanged();
    }

    private void filterByTaste(SortType sortType, Taste taste){
        if (taste == taste.All)
            this.taste = taste;
        else{
            list = SnackItemService.getSnackByTaste(taste, sortType, 10);
        }
        notifyDataSetChanged();
    }




}
