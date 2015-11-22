package com.hexad.snackmate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hexad.snackmate.Utils.ImageLoader;
import com.hexad.snackmate.Utils.ImageResizer;
import com.hexad.snackmate.Utils.Utils;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

/**
 * Created by Michael Ji on 11/10/2015.
 *
 * Display profile including current user email, name and buttons to signin/signout
 * and to go to homepage
 */
public class ProfileActivity extends Activity {
    private static final int LOGIN_REQUEST = 0;
    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private TextView guestTextView;
    private Button loginOrLogoutButton;
    private Button HomePageButton;
    private ParseUser currentUser;
    private ImageView profilePicImageView;
    private Button guestLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_profile);
        profilePicImageView = (ImageView) findViewById(R.id.profile_profile_pic);
        titleTextView = (TextView) findViewById(R.id.profile_title);
        emailTextView = (TextView) findViewById(R.id.profile_email);
        nameTextView = (TextView) findViewById(R.id.profile_name);
        guestTextView = (TextView) findViewById(R.id.profile_guest);
        titleTextView.setText(R.string.profile_title_logged_in);


        setListenersForButtons();


    }

    private void setListenersForButtons(){
        loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);
        HomePageButton = (Button) findViewById(R.id.homepage_button);
        guestLoginButton = (Button) findViewById(R.id.guest_login_button);
//
//        if(currentUser!=null) {
//            HomePageButton.setVisibility(View.VISIBLE);
//        }
//        else
//            HomePageButton.setVisibility(View.GONE);


        loginOrLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    // User clicked to log out.
                    ParseUser.logOut();
                    currentUser = null;
                    showProfileLoggedOut();
                } else {
                    // User clicked to log in.
                    ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
                            ProfileActivity.this);
                    startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);
                }
            }
        });

        HomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, HomePageActivity.class));
            }
        });

        guestLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseAnonymousUtils.logIn(new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e != null) {
                            Log.d("SnackMate", "Anonymous login failed.");
                        } else {
                            Log.d("SnackMate", "Anonymous user logged in.");
                            Global.currentUser = user;
                            Global.currentUser.put("name","Guest");
                        }
                    }
                });

                startActivity(new Intent(ProfileActivity.this, HomePageActivity.class));
            }
        });
    }

        @Override
    protected void onStart() {
        super.onStart();

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            if (ParseAnonymousUtils.isLinked(currentUser)){
                currentUser.logOutInBackground();
                showProfileLoggedOut();
            }
            else
                showProfileLoggedIn();
        } else {
            showProfileLoggedOut();
        }
    }

    /**
     * Shows the profile of the given user.
     */
    private void showProfileLoggedIn() {
        if (currentUser.getParseFile("profilePicture") != null){
            ImageLoader imageLoader = new ImageLoader(this);
            imageLoader.displayImage(currentUser.getParseFile("profilePicture").getUrl(),
                    profilePicImageView);
        }
        else
            profilePicImageView.setVisibility(View.GONE);

        emailTextView.setVisibility(View.VISIBLE);
        nameTextView.setVisibility(View.VISIBLE);

        titleTextView.setText(R.string.profile_title_logged_in);
        emailTextView.setText(currentUser.getEmail());
        String fullName = currentUser.getString("name");
        if (fullName != null) {
            nameTextView.setText(fullName);
        }
        loginOrLogoutButton.setText(R.string.profile_logout_button_label);
        guestTextView.setVisibility(View.GONE);
        HomePageButton.setVisibility(View.VISIBLE);
        guestLoginButton.setVisibility(View.GONE);
    }

    /**
     * Show a message asking the user to log in, toggle login/logout button text.
     */
    private void showProfileLoggedOut() {
        profilePicImageView.setVisibility(View.GONE);
        titleTextView.setText(R.string.profile_title_logged_out);
        emailTextView.setVisibility(View.GONE);
        nameTextView.setVisibility(View.GONE);
        loginOrLogoutButton.setText(R.string.profile_login_button_label);
        HomePageButton.setVisibility(View.GONE);
        guestTextView.setVisibility(View.VISIBLE);
        guestTextView.setText(R.string.profile_title_guest_login);
        guestLoginButton.setVisibility(View.VISIBLE);
    }
}
