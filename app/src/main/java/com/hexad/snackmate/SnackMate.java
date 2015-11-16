package com.hexad.snackmate;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.hexad.snackmate.Items.LineItem;
import com.hexad.snackmate.Items.SnackItem;
import com.hexad.snackmate.Items.SnackItemService;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

/**
 * Created by Michael Ji on 11/10/2015.
 *
 * class that runs once the application starts to load up services and get data from cloud
 */
public class SnackMate extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Required - Initialize the Parse SDK
        //set up for parse objects
        ParseObject.registerSubclass(SnackItem.class);
        ParseObject.registerSubclass(LineItem.class);
        //Some test code for Parse
        Parse.enableLocalDatastore(this);
        Parse.initialize(this,getString(R.string.parse_app_id),getString(R.string.parse_client_key));

//        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        FacebookSdk.sdkInitialize(getApplicationContext());
        ParseFacebookUtils.initialize(this);

        // Optional - If you don't want to allow Twitter login, you can
        // remove this line (and other related ParseTwitterUtils calls)
        ParseTwitterUtils.initialize(getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));

        Global.list = SnackItemService.getAllSnackItemsSync();

    }
}
