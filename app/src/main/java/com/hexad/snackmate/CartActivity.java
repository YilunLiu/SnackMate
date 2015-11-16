package com.hexad.snackmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        itemList = ParseUser.getCurrentUser().getList("cart");

        CartItemAdapter adapter = new CartItemAdapter(CartActivity.this, R.layout.cart_item, itemList);
        list = (ListView) findViewById(R.id.cart);
        list.setAdapter(adapter);



    }
}
