package com.hexad.snackmate.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;

import com.hexad.snackmate.Models.SnackItem;
import com.hexad.snackmate.R;
import com.hexad.snackmate.Listeners.SwipeDismissListViewTouchListener;
import com.hexad.snackmate.ViewAdapters.WishListItemAdapter;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by AxlWu on 2015/11/12.
 */
public class WishListActivity extends AppCompatActivity {

        ListView list;
        List<SnackItem> itemList;

        @Override
        protected void onCreate(Bundle SavedInstanceState) {
            super.onCreate(SavedInstanceState);
            setContentView(R.layout.wishlist);
            setTitle("Wishlist");
            try {
                itemList = ParseUser.getCurrentUser().fetchIfNeeded().getList("wishList");
            }catch (Exception e){}
            list = (ListView) findViewById(R.id.wish_list);
            if(itemList == null) {
//                itemList = new ArrayList<SnackItem>();
                list.setEmptyView(findViewById(R.id.empty));
            }
            else {
                findViewById(R.id.empty).setVisibility(View.GONE);
                final WishListItemAdapter adapter = new WishListItemAdapter(WishListActivity.this, R.layout.wishlist_item, itemList);

//              list.setEmptyView(findViewById(R.id.empty));
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
                                        ParseUser.getCurrentUser().put("wishList", itemList);
                                        ParseUser.getCurrentUser().saveInBackground();
                                    }
                                });
                list.setOnTouchListener(touchListener);
                // Setting this scroll listener is required to ensure that during ListView scrolling,
                // we don't look for swipes.
                list.setOnScrollListener(touchListener.makeScrollListener());

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(WishListActivity.this, ItemDetailActivity.class);
                        intent.putExtra("itemid", itemList.get(position).getObjectId());
                        startActivity(intent);
                    }
                });
            }

        }
}
