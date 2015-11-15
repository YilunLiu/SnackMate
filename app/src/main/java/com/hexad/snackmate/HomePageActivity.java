package com.hexad.snackmate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hexad.snackmate.Activities.UploadImageActivity;
import com.hexad.snackmate.Items.LineItem;
import com.hexad.snackmate.Items.SnackItem;
import com.hexad.snackmate.Utils.ImageLoader;
import com.hexad.snackmate.Utils.ImageResizer;
import com.hexad.snackmate.Utils.Utils;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginActivity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int LOGIN_REQUEST = 0;
    private Spinner filter,sort;
    private ParseUser currentUser;

    private static final int UPLOAD_ACTIVITY_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        ParseUser user = ParseUser.getCurrentUser();
        if (user.getList("wishList") == null){
            user.put("wishList", new ArrayList<SnackItem>());
            user.saveInBackground();
        }
        if (user.getList("cart") == null){
            user.put("cart", new ArrayList<LineItem>());
            user.saveInBackground();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set up gridview on the homepage
        GridView gridView = (GridView) findViewById(R.id.homepage_gridview);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(HomePageActivity.this, ItemDetailActivity.class);
                intent.putExtra("itemid", position);
                startActivity(intent);

            }
        });
        //Set up spinner objects and add listeners for them
        addItemsOnSpinners();
        addListenerOnSpinnerItemSelection();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case UPLOAD_ACTIVITY_REQUEST:{
                if (resultCode == RESULT_OK && data != null){
                    ImageView profileImage = (ImageView) findViewById(R.id.nav_profile_image);
                    String picturePath = data.getStringExtra("picturePath");
                    Bitmap bitmap = ImageResizer.decodeSampledBitmapFromFile(picturePath, 500, 500);
                    profileImage.setImageBitmap(Utils.getCircleBitmap(bitmap));
                    profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        currentUser = ParseUser.getCurrentUser();
        String username = currentUser != null ? currentUser.getString("name") : "Guest";

        ImageView navImageView = (ImageView) findViewById(R.id.nav_profile_image);
        TextView navUsernameView = (TextView) findViewById(R.id.nav_username);
        navUsernameView.setText("Hello," + username);

        if (ParseUser.getCurrentUser() != null){
            ImageLoader imageLoader = new ImageLoader(this);
            imageLoader.displayImage(ParseUser.getCurrentUser().getParseFile("profilePicture").getUrl(),
                    navImageView);
        }


        navImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (currentUser != null) {
                    intent = new Intent(HomePageActivity.this, UploadImageActivity.class);
                    startActivityForResult(intent,UPLOAD_ACTIVITY_REQUEST);
                } else {
                    intent = new Intent(HomePageActivity.this, ParseLoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_shuffle) {
            Intent intent = new Intent(HomePageActivity.this, ShuffleActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_wish) {
            // Handle the camera action
            Intent intent = new Intent(HomePageActivity.this, WishListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history){

        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(HomePageActivity.this, CartActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addItemsOnSpinners(){
        filter = (Spinner) findViewById(R.id.spinner1);
        String[] names = new String[]{"Filter","Origins", "Tastes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(adapter);


        sort = (Spinner) findViewById(R.id.spinner2);
        String[] names1 = new String[]{"Sort", "Price high to low", "Price low to high", "Ratings", "Alphabetical Order"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(adapter1);

    }

    public void addListenerOnSpinnerItemSelection() {

        filter.setOnItemSelectedListener(new SpinnerActivity());
        sort.setOnItemSelectedListener(new SpinnerActivity());

    }


}
