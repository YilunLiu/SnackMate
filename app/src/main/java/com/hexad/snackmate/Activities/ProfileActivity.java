package com.hexad.snackmate.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hexad.snackmate.R;
import com.hexad.snackmate.Utils.Image.ImageLoader;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

/**
 * Created by Michael Ji on 11/10/2015.
 *
 * Display profile including current user email, name and buttons to sign in/sign out
 * and to go to homepage
 */
public class ProfileActivity extends Activity {
    //declare views and buttons
    private static final int LOGIN_REQUEST = 0;
    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private Button loginOrLogoutButton;
    private Button HomePageButton;
    private ImageView profilePicImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //setup views for display
        profilePicImageView = (ImageView) findViewById(R.id.profile_profile_pic);
        titleTextView = (TextView) findViewById(R.id.profile_title);
        emailTextView = (TextView) findViewById(R.id.profile_email);
        nameTextView = (TextView) findViewById(R.id.profile_name);
        titleTextView.setText(R.string.profile_title_logged_in);

        //set listeners for buttons
        setListenersForButtons();


    }

    /**
     *     Set listener for buttons
     * @Param None
     * @return None
     */
    private void setListenersForButtons(){
        //get buttons
        loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);
        HomePageButton = (Button) findViewById(R.id.homepage_button);

        loginOrLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if user is not Guest, log out
                if(!ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
                    ParseUser.logOut();
                }

                //go to login page
                ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
                        ProfileActivity.this);
                startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);
            }
        });

        HomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //go to homepage
                startActivity(new Intent(ProfileActivity.this, HomePageActivity.class));
            }
        });

    }

        @Override
    protected void onStart() {
        super.onStart();

        //on activity start, show user profile page
        showProfile();
    }

    /**
     * Shows the profile of the given user.
     */
    private void showProfile() {

        //display profile picture
        if (ParseUser.getCurrentUser().getParseFile("profilePicture") != null){
            ImageLoader imageLoader = new ImageLoader(this);
            imageLoader.displayImage(ParseUser.getCurrentUser().getParseFile("profilePicture").getUrl(),
                    profilePicImageView);
        }
        else {
            //if no profile picture, hide the view and do not take space in window
            profilePicImageView.setVisibility(View.GONE);
        }


        //display textview
        emailTextView.setVisibility(View.VISIBLE);
        nameTextView.setVisibility(View.VISIBLE);
        titleTextView.setText(R.string.profile_title_logged_in);
        emailTextView.setText(ParseUser.getCurrentUser().getEmail());

        String fullName = ParseUser.getCurrentUser().getString("name");
        //display user name if actual user, else guest
        if (fullName != null) {
            loginOrLogoutButton.setText("Log out");
            nameTextView.setText(fullName);
        }
        else{
            nameTextView.setText("Guest");
            loginOrLogoutButton.setText("Sign In");

        }
        HomePageButton.setVisibility(View.VISIBLE);
    }

}
