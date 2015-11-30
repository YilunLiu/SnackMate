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


        loginOrLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
                            ProfileActivity.this);
                startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);
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

                startActivity(new Intent(ProfileActivity.this, HomePageActivity.class));
            }
        });
    }

        @Override
    protected void onStart() {
        super.onStart();
        showProfileLoggedIn();
    }

    /**
     * Shows the profile of the given user.
     */
    private void showProfileLoggedIn() {
        if (ParseUser.getCurrentUser().getParseFile("profilePicture") != null){
            ImageLoader imageLoader = new ImageLoader(this);
            imageLoader.displayImage(ParseUser.getCurrentUser().getParseFile("profilePicture").getUrl(),
                    profilePicImageView);
        }
        else {
            profilePicImageView.setVisibility(View.GONE);
        }

        emailTextView.setVisibility(View.VISIBLE);
        nameTextView.setVisibility(View.VISIBLE);

        titleTextView.setText(R.string.profile_title_logged_in);
        emailTextView.setText(ParseUser.getCurrentUser().getEmail());
        String fullName = ParseUser.getCurrentUser().getString("name");
        if (fullName != null) {
            loginOrLogoutButton.setText("Log out");
            nameTextView.setText(fullName);
        }
        else{
            nameTextView.setText("Guest");
            loginOrLogoutButton.setText("Sign In");

        }
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
