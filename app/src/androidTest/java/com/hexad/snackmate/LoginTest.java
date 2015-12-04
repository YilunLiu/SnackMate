package com.hexad.snackmate;

import android.app.Activity;
import android.app.Application;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.test.ApplicationTestCase;

/**
 * Created by yilunliu on 12/3/15.
 */
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.hexad.snackmate.Activities.HomePageActivity;
import com.hexad.snackmate.Activities.ItemDetailActivity;
import com.hexad.snackmate.Activities.ProfileActivity;
import com.hexad.snackmate.Models.SnackItem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.*;
import static org.hamcrest.Matchers.*;


/**
 * 1. Normal browsing
 Given the user opens the app
 And the homepage shows up
 When user taps on an item (either picture or title)
 Then a detailed item page shows up
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

    @Rule
    public ActivityTestRule<ProfileActivity> mActivityRule = new ActivityTestRule<>(ProfileActivity.class);

    @Test
    /**
     * Given: user opens SnackMate
     * When: homepage shows up and click "Go To HomePage" button
     * Then: HomePage shows up
     */
    public void HomePageButton() {
        // test on homepage button
        onView(withId(R.id.homepage_button)).perform(ViewActions.click());
        onView(withId(R.id.home)).check(matches(isDisplayed()));
    }

    @Test
    /**
     * Given: user opens SnackMate
     * When: user is not logged in, click sign in button
     * Then: jump to sign in page and able to sign in with an existing id
     */
    public void SignInButton(){
        // test on new user or logged in user
        onView(withId(R.id.login_or_logout_button)).perform(ViewActions.click());
        // login for new user, logout for logged in user
        onView(withId(R.id.parse_login)).check(matches(isDisplayed()));

        // test on an existing user
        onView(withId(R.id.login_username_input)).perform(ViewActions.typeText("axlwu1994@gmail.com"));
        onView(withId(R.id.login_password_input)).perform(ViewActions.typeText("wuchunhao"));
        // click login button, jump to profile page
        onView(withId(R.id.parse_login_button)).perform(ViewActions.click());
        onView(withId(R.id.profile)).check(matches(isDisplayed()));

    }

    @Test
    /**
     * Given: user opens SnackMate
     * When: user is not logged in, click sign in button, or user is already logged in, log out
     * Then: jump to sign up page
     */
    public void SignUpButton(){
        // test on new user or logged in user
        onView(withId(R.id.login_or_logout_button)).perform(ViewActions.click());
        // click on signup button
        onView(withId(R.id.parse_signup_button)).perform(ViewActions.click());
        onView(withId(R.id.signup_page)).check(matches(isDisplayed()));

//        // sign up as a new user
//        onView(withId(R.id.signup_email_input)).perform(ViewActions.typeText("axlwu@ucsd.edu\n"),ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.signup_password_input)).perform(ViewActions.typeText("wuchunhao"));
//        onView(withId(R.id.signup_confirm_password_input)).perform(ViewActions.typeText("wuchunhao"));
//        onView(withId(R.id.signup_name_input)).perform(ViewActions.typeText("axlwu test"));
//
//        onView(withId(R.id.create_account)).perform(ViewActions.click());
//        // click login button, jump to profile page
//        onView(withId(R.id.profile)).check(matches(isDisplayed()));
    }
}
