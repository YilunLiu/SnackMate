package com.hexad.snackmate.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.hexad.snackmate.Enumerations.Country;
import com.hexad.snackmate.Enumerations.Taste;
import com.hexad.snackmate.Services.SnackItemService;
import com.hexad.snackmate.Utils.Image.ImageHelper;
import com.hexad.snackmate.ViewApadters.HomePageAdapter;
import com.hexad.snackmate.Models.LineItem;
import com.hexad.snackmate.Models.SnackItem;
import com.hexad.snackmate.Views.MultiLayerMenuView;
import com.hexad.snackmate.R;
import com.hexad.snackmate.Framgments.SelectFragment;
import com.hexad.snackmate.Framgments.SortFragment;
import com.hexad.snackmate.ViewApadters.SubMultiMenuAdapter;
import com.hexad.snackmate.Utils.Image.ImageLoader;
import com.hexad.snackmate.Utils.Image.ImageResizer;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.util.ArrayList;
import java.util.List;


public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener,
                    SearchView.OnCloseListener {

    private static final int LOGIN_REQUEST = 0;

    private static final int UPLOAD_ACTIVITY_REQUEST = 1;


    String filters[] = new String[]{"Origin", "Taste"};

    String sort[] = new String[]{"Price", "Rating", "Name"};

    String choices[] =  new String[]{Country.All.toString(),Country.China.toString(), Country.Japan.toString(), Country.North_Korea.toString(), Country.Others.toString(),
                    Taste.All.toString(),Taste.Sweet.toString(), Taste.Sour.toString(), Taste.Spicy.toString(), Taste.Salty.toString(), Taste.Others.toString()};

    String choices2[] = new String[]{"Low to High", "High to Low", "A to Z","Z to A"};

    private View choseSubject;
    private View choseSubject2;
    private MultiLayerMenuView subListView;
    private SubMultiMenuAdapter subAdapter;
    private ArrayAdapter<String> filter_adapter;
    private GridView gridView;

    private SearchView searchView;
    private HomePageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        if (!ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            if (ParseUser.getCurrentUser().getList("wishList") == null) {
                ParseUser.getCurrentUser().put("wishList", new ArrayList<SnackItem>());
                ParseUser.getCurrentUser().saveInBackground();
            }
            if (ParseUser.getCurrentUser().getList("cart") == null) {
                ParseUser.getCurrentUser().put("cart", new ArrayList<LineItem>());
                ParseUser.getCurrentUser().saveInBackground();
            }
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

        imageAdapter = new HomePageAdapter(this, SnackItemService.list);
        GridView gridView = (GridView) findViewById(R.id.homepage_gridview);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(HomePageActivity.this, ItemDetailActivity.class);
                intent.putExtra("itemid", position);
                startActivity(intent);

            }
        });

        //Set up spinner objects and add listeners for them
//        init();
        addItemsOnSpinners();
        addListenerOnSpinnerItemSelection();


    }

//    private void init(){
//        subListView=(MultiLayerMenu) findViewById(R.id.subListView);
//        subListView.setBackgroundColor(Color.WHITE);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPLOAD_ACTIVITY_REQUEST: {
                if (resultCode == RESULT_OK && data != null) {
                    ImageView profileImage = (ImageView) findViewById(R.id.nav_profile_image);
                    String picturePath = data.getStringExtra("picturePath");
                    Bitmap bitmap = ImageResizer.decodeSampledBitmapFromFile(picturePath, 500, 500);
                    profileImage.setImageBitmap(ImageHelper.getCircleBitmap(bitmap));
                    profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        Fragment fg = getFragmentManager().findFragmentByTag("choiceSubject");
        Fragment fg2 = getFragmentManager().findFragmentByTag("choiceSubject2");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (fg != null && ((SelectFragment) fg).onBackPressed()) {
            return;
        } else if (fg2 != null && ((SortFragment) fg2).onBackPressed()) {
            return;
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView();

        String username = ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser()) ? "Guest" : ParseUser.getCurrentUser().getString("name");

        TextView navUsernameView = (TextView) findViewById(R.id.nav_username);
        navUsernameView.setText("Hello, " + username);

        ImageView navImageView = (ImageView) findViewById(R.id.nav_profile_image);

        navImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (ParseUser.getCurrentUser() != null) {
                    intent = new Intent(HomePageActivity.this, UploadImageActivity.class);
                    startActivityForResult(intent, UPLOAD_ACTIVITY_REQUEST);
                } else {
                    ParseLoginBuilder loginBuilder = new ParseLoginBuilder(HomePageActivity.this);
                    startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);
                }
            }
        });

        if (!ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser()) &&
                ParseUser.getCurrentUser().getParseFile("profilePicture") != null) {
            ImageLoader imageLoader = new ImageLoader(this);
            imageLoader.displayImage(ParseUser.getCurrentUser().getParseFile("profilePicture").getUrl(),
                    navImageView);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);

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
        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(HomePageActivity.this, CartActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_contact) {
            startActivity(new Intent(HomePageActivity.this, ContactUsActivity.class));

        } else if (id == R.id.nav_logout) {

            if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
                ParseUser.logOut();
                startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
            } else {
                ParseUser.logOut();
                ParseLoginBuilder loginBuilder = new ParseLoginBuilder(HomePageActivity.this);
                startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addItemsOnSpinners() {
        choseSubject = findViewById(R.id.ll_select_subject);
        choseSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] location = new int[]{choseSubject.getLeft(), choseSubject.getTop()};
                FragmentManager fm = getFragmentManager();
                SelectFragment sf = SelectFragment.newInstance(location, filters, choices);
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.fl_subject_fragment, sf, "choiceSubject");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        choseSubject2 = findViewById(R.id.ll_select_subject2);
        choseSubject2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] location = new int[]{choseSubject2.getLeft(), choseSubject2.getTop()};
                FragmentManager fm2 = getFragmentManager();
                SortFragment sf2 = SortFragment.newInstance(location, sort,choices2);
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.add(R.id.fl_subject_fragment2, sf2, "choiceSubject2");
                ft2.addToBackStack(null);
                ft2.commit();
            }
        });


    }

    public void addListenerOnSpinnerItemSelection() {


//        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View arg1, int position,
//                                       long arg3) {
//
//                // TODO Auto-generated method stub
//                subAdapter = new SubMultiMenuAdapter(getApplicationContext(), choices, position);
//
//                    final int location = position;
//                    final String filter_type = parent.getItemAtPosition(position).toString();
//                    if (filter_type != "Filter"){
//                    subListView.setVisibility(View.VISIBLE);
//                    subListView.setAdapter(subAdapter);
//
//                    subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> arg0, View arg1,
//                                                int arg2, long arg3) {
//
//                            subListView.setVisibility(View.GONE);
//                            String selected = choices[location][arg2];
//                            if (filter_type == "Filter") {
//
//                                return;
//                            }
//                            else if (filter_type == "Origins") {
//                                Country country = Country.All;
//                                switch (selected) {
//                                    case "China":
//                                        country = Country.China;
//                                        break;
//                                    case "Japan":
//                                        country = Country.Japan;
//                                        break;
//                                    case "Others":
//                                        country = Country.Others;
//                                        break;
//                                    case "North Korea":
//                                        country = Country.North_Korea;
//                                        break;
//                                    case "All":
//                                        imageAdapter.setList(SnackItemService.list);
//                                        return;
//                                }
//                                imageAdapter.filterByCountry(country);
//                            } else {
//                                Taste taste = Taste.All;
//                                switch (selected) {
//                                    case "Sweet":
//                                        taste = Taste.Sweet;
//                                        break;
//                                    case "Sour":
//                                        taste = Taste.Sour;
//                                        break;
//                                    case "Salty":
//                                        taste = Taste.Salty;
//                                        break;
//                                    case "Spicy":
//                                        taste = Taste.Spicy;
//                                        break;
//                                    case "Others":
//                                        taste = Taste.Others;
//                                        break;
//                                    case "All":
//                                        imageAdapter.setList(SnackItemService.list);
//                                        return;
//                                }
//                                imageAdapter.filterByTaste(taste);
//                            }
//                            filter_adapter.notifyDataSetChanged();
//                            subAdapter.notifyDataSetChanged();
//                        }
//                    });
//                    }
//                    else{
//                        subListView.setVisibility(View.GONE);
//                    }
//
//            }
//
//
//            public void onNothingSelected(AdapterView<?> parent)
//            {
//
//            }
//        });

    }



    /* Search View Methods */

    /*
        Set up search view with Search manager
        @param None
        @return None
     */
    private void setupSearchView() {

        searchView.setIconifiedByDefault(true);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
    }

    @Override
    /*
        Text view listener method
        @param: string newText - new text entered
        @return: bool - the case is handled already
     */
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    /*
        search view listener method
        @param: String query - the text is queryed by search view
        @return: bool - indicate if the case is handled already
     */
    public boolean onQueryTextSubmit(String query) {

        Log.d("Searchable", "Query: " + query);
        List<SnackItem> list = new ArrayList<SnackItem>();
        for (SnackItem snackItem : SnackItemService.list) {
            if (snackItem.getTitle().contains(query)) {
                list.add(snackItem);
            }
        }
        imageAdapter.setList(list);
        imageAdapter.notifyDataSetChanged();

        return false;
    }

    @Override
    /*
        search view listener method, called on close the listener
        @param: None
        @return: bool - indicate if the case is handled already
     */
    public boolean onClose() {
        return false;
    }
}