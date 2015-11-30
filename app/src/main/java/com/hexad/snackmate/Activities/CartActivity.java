package com.hexad.snackmate.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.hexad.snackmate.ViewAdapters.CartItemAdapter;
import com.hexad.snackmate.Models.LineItem;
import com.hexad.snackmate.R;
import com.hexad.snackmate.Listeners.SwipeDismissListViewTouchListener;
import com.parse.ParseUser;

import java.util.List;
/**
 * Created by Lun Li
 * show the shopping cart
 */
public class CartActivity extends AppCompatActivity {

    ListView list;
    List<LineItem> itemList;

    /**
     * onCreate
     * @param SavedInstanceState
     * return void
     */
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Cart");
        // get the data
        try {
            itemList = ParseUser.getCurrentUser().fetchIfNeeded().getList("cart");
        }catch (Exception e){}

        list = (ListView) findViewById(R.id.cart);
        if (itemList == null)
        list.setEmptyView(findViewById(R.id.cart_empty));
        // show the list
        else {
            findViewById(R.id.cart_empty).setVisibility(View.GONE);

            final CartItemAdapter adapter = new CartItemAdapter(CartActivity.this, R.layout.cart_item, itemList);

            list.setAdapter(adapter);
            // implement the deleting an item from the cart
            SwipeDismissListViewTouchListener touchListener =
                    new SwipeDismissListViewTouchListener(
                            list,
                            new SwipeDismissListViewTouchListener.DismissCallbacks() {
                                @Override
                                public boolean canDismiss(int position) {
                                    return true;
                                }

                                // handle the deleted item
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
