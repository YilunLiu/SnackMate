package com.hexad.snackmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;

import com.hexad.snackmate.Items.SnackItem;
import com.parse.ParseUser;

import java.util.ArrayList;
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
                WishListItemAdapter adapter = new WishListItemAdapter(WishListActivity.this, R.layout.wishlist_item, itemList);

//            list.setEmptyView(findViewById(R.id.empty));
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(WishListActivity.this, ItemDetailActivity.class);
                        intent.putExtra("itemid", position);
                        startActivity(intent);
                    }
                });
            }

        }
}
