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
public class HomePageTest {

    @Rule
    public ActivityTestRule<HomePageActivity> mActivityRule = new ActivityTestRule<>(HomePageActivity.class);

    @Test
    public void clickTheItem() {
        onData(anything()).inAdapterView(withId(R.id.homepage_gridview)).atPosition(0).perform(ViewActions.click());

        onView(withId(R.id.image_itemdetail_view)).check(matches(isDisplayed()));
        //onView(withText("1")).check(matches(isDisplayed()));
    }
}
