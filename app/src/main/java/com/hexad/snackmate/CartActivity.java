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
            CartItemAdapter adapter = new CartItemAdapter(CartActivity.this, R.layout.cart_item, itemList);

            list.setAdapter(adapter);
        }


    }
}
