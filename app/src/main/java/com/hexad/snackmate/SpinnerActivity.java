package com.hexad.snackmate;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.hexad.snackmate.Enumerations.SortType;

/**
 * Created by Michael on 2015/11/1.
 */
public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Context c;
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String selected = parent.getItemAtPosition(pos).toString();
        c = parent.getContext();
        Activity activity = (Activity)c;
        GridView gridView = (GridView) activity.findViewById(R.id.homepage_gridview);
        ImageAdapter adapter = (ImageAdapter) gridView.getAdapter();
//        Toast.makeText(c,
//                "OnItemSelectedListener : " + selected,
//                Toast.LENGTH_SHORT).show();
        SortType sortType = SortType.Default;

        switch(selected) {

            case "Sort":
                return;
            case "Price high to low":
                sortType = SortType.Price_high_to_low;
                break;
            case "Price low to high":
                sortType = SortType.Price_low_to_high;
                break;
            case "Ratings":
                sortType = SortType.Rating;
                break;
            case "Alphabetical Order":
                sortType = SortType.Alphabetical;
                break;
            default:
        }

        adapter.SortByType(sortType);

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
