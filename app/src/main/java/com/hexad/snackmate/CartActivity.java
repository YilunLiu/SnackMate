package com.hexad.snackmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hexad.snackmate.Items.LineItem;
import com.hexad.snackmate.Items.SnackItem;
import com.parse.ParseUser;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    ListView list;
    List<LineItem> itemList;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Cart");
        try {
            itemList = ParseUser.getCurrentUser().fetchIfNeeded().getList("cart");
        }catch (Exception e){}

        list = (ListView) findViewById(R.id.cart);
        if (itemList == null)
        list.setEmptyView(findViewById(R.id.cart_empty));

        else {
            findViewById(R.id.cart_empty).setVisibility(View.GONE);
            Log.d("cart size", Integer.toString(itemList.size()));
            final CartItemAdapter adapter = new CartItemAdapter(CartActivity.this, R.layout.cart_item, itemList);

            list.setAdapter(adapter);
            SwipeDismissListViewTouchListener touchListener =
                    new SwipeDismissListViewTouchListener(
                            list,
                            new SwipeDismissListViewTouchListener.DismissCallbacks() {
                                @Override
                                public boolean canDismiss(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                    for (int position : reverseSortedPositions) {

                                        adapter.remove(adapter.getItem(position));

                                    }
                                    adapter.notifyDataSetChanged();

                                    ParseUser.getCurrentUser().put("cart", itemList);
                                    ParseUser.getCurrentUser().saveInBackground();
                                }
                            });
            list.setOnTouchListener(touchListener);
            // Setting this scroll listener is required to ensure that during ListView scrolling,
            // we don't look for swipes.
            list.setOnScrollListener(touchListener.makeScrollListener());

        }


    }
}
