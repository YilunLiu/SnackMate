package com.hexad.snackmate;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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
        Toast.makeText(c,
                "OnItemSelectedListener : " + selected,
                Toast.LENGTH_SHORT).show();

        switch(selected) {
            case "Filter":
            case "Sort":
            case "Origin":
            case "Taste":
            case "Price high to low":
            case "Price low to high":

                GridView gridView = (GridView) activity.findViewById(R.id.homepage_gridview);
                Toast.makeText(c,
                        "Success : " + gridView.getAdapter().getCount(),
                        Toast.LENGTH_SHORT).show();
                break;
            case "Ratings":
            case "Alphabetical Order":
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
