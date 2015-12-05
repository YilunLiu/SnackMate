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
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.hexad.snackmate.Activities.HomePageActivity;
import com.hexad.snackmate.Activities.ItemDetailActivity;
import com.hexad.snackmate.Models.SnackItem;
import com.hexad.snackmate.ViewAdapters.HomePageAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.*;
import static org.hamcrest.Matchers.*;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomePageTest {

    @Rule
    public ActivityTestRule<HomePageActivity> mActivityRule = new ActivityTestRule<>(HomePageActivity.class);


    /**
     * 1. Normal browsing
     Given the user opens the app
     And the homepage shows up
     When user taps on an item (either picture or title)
     Then a detailed item page shows up
     */
    @Test
    public void clickTheItem() {
        onData(anything()).inAdapterView(withId(R.id.homepage_gridview)).atPosition(0).perform(ViewActions.click());

        onView(withId(R.id.image_itemdetail_view)).check(matches(isDisplayed()));
        //onView(withText("1")).check(matches(isDisplayed()));
    }


    /*4. Filtering
    Given the user is on homepage
    When user taps on filtering
    Then a dropdown menu shows up
    When user taps on (a category)
    Then the dropdown menu disappears
    And only the relevant items are on the homepage*/

    @Test
    public void testFilterByCountry(){

        onView(withId(R.id.ll_select_subject)).perform(ViewActions.click());
        onData(anything()).inAdapterView(withId(R.id.lv_menu)).atPosition(0).perform(ViewActions.click());
        onData(anything()).inAdapterView(withId(R.id.lv_subject)).atPosition(1).perform(ViewActions.click());

        onView(withId(R.id.homepage_gridview)).check(matches(filteredByCountry("China")));

    }

    // custom matcher for checking filter
    public static Matcher<View> filteredByCountry(final String country){
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof GridView)) {
                    return false;
                }
                ListAdapter adapter = ((GridView) view).getAdapter();
                for(int i = 0; i < adapter.getCount(); i++) {
                    SnackItem item = (SnackItem) adapter.getItem(i);
                    if (!item.getCountry().toString().equals(country))
                        return false;
                }
                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }
}
