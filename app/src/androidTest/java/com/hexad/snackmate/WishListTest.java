package com.hexad.snackmate;

import android.app.Application;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
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
import com.hexad.snackmate.Models.SnackItem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.*;
import static org.hamcrest.Matchers.*;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class WishListTest {

    @Rule
    public ActivityTestRule<HomePageActivity> mActivityRule = new ActivityTestRule<>(HomePageActivity.class);


    /* Scenario : add to wish list with an item
        Given a user is on the home page,
        And does not have any wishlist item,
        And home page displays some items,
        When the user clicks a item on the item,
        And clicks on a add to wish list,
        And goes to wish list,
        Then the user sees a wishlist item
     */
    @Test
    public void AddToWishList() {

        // click to go to item detail page
        onData(anything()).inAdapterView(withId(R.id.homepage_gridview)).atPosition(0).perform(ViewActions.click());
        // add to wish list
        onView(withId(R.id.item_detail_wish)).perform(ViewActions.click());
        // goes to wishlist page
        pressBack();
//        DrawerActions.openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Wish List")).perform(ViewActions.click());
        // then there should be one item
        onData(instanceOf(SnackItem.class)).inAdapterView(withId(R.id.wish_list)).atPosition(0).check(matches(isDisplayed()));

    }

    /* Scenario : add to wish list with same item twice
        Given a user is on the home page,
        And does not have any wishlist item,
        And home page displays some items,
        When the user clicks a item on the item,
        And clicks on a add to wish list,
        And the user clicks the same item again to add to wishlist,
        And goes to wish list,
        Then the user sees only one wishlist item
     */
    @Test
    public void AddSameItemtoWishListTwice() {

        // click to go to item detail page
        onData(anything()).inAdapterView(withId(R.id.homepage_gridview)).atPosition(0).perform(ViewActions.click());
        // add to wish list twice
        onView(withId(R.id.item_detail_wish)).perform(ViewActions.click());
        onView(withId(R.id.item_detail_wish)).perform(ViewActions.click());
        // goes to wishlist page
        pressBack();
//        DrawerActions.openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Wish List")).perform(ViewActions.click());
        // then there should be one item
        onData(instanceOf(SnackItem.class)).inAdapterView(withId(R.id.wish_list)).atPosition(0).check(matches(isDisplayed()));

    }

    /* Scenario : add to wish list
        Given a user is on the home page,
        And does not have any wishlist item,
        And home page displays some items,
        When the user clicks a item on the item,
        And clicks on a add to wish list,
        And clicks back
        And the user clicks the another item on homeage to add to wishlist,
        And goes to wish list,
        Then the user sees two wishlist item
    */
    @Test
    public void AddTwoDistinctItemsToWishList() {

        // click to go to item detail page
        onData(anything()).inAdapterView(withId(R.id.homepage_gridview)).atPosition(0).perform(ViewActions.click());
        // add to wish list
        onView(withId(R.id.item_detail_wish)).perform(ViewActions.click());
        // goes back to home page
        pressBack();
        // selects another item and add to wishlist
        onData(anything()).inAdapterView(withId(R.id.homepage_gridview)).atPosition(1).perform(ViewActions.click());
        onView(withId(R.id.item_detail_wish)).perform(ViewActions.click());
        // goes back to homepage
        pressBack();
//        DrawerActions.openDrawer(R.id.drawer_layout);
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Wish List")).perform(ViewActions.click());
        // then there should be two item
        onData(instanceOf(SnackItem.class)).inAdapterView(withId(R.id.wish_list)).atPosition(0).check(matches(isDisplayed()));

    }
}
