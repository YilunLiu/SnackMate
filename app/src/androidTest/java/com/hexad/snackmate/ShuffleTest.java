package com.hexad.snackmate;

import android.app.Application;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.test.ApplicationTestCase;

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

import java.util.regex.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by Yijun on 12/4/2015.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShuffleTest {

    @Rule
    public ActivityTestRule<HomePageActivity> mActivityRule = new ActivityTestRule<>(HomePageActivity.class);

/*
    Given the user is on homepage
    When the user taps the button on the top right corner
    Then the shuffle page shows up with a random snack picture in the middle

    1) When the user swipes left
    Or taps the dislike button
    Then the animation plays
    And a new picture shows up

    2) When the user swipes right
    Or taps like button
    Then the animation plays
    And a new picture shows up
    And the item is added to the wish list
*/

    @Test
    public void Shuffle() {

        // click to go to shuffle page
        onView(withId(R.id.action_shuffle)).perform(ViewActions.click());
        // like by clicking the button
        onView(withId(R.id.wish_list_button)).perform(ViewActions.click());
        //goes to wish list page
        pressBack();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Wish List")).perform(ViewActions.click());
        // then there should be one item
        onData(instanceOf(SnackItem.class)).inAdapterView(withId(R.id.wish_list)).atPosition(0).check(matches(isDisplayed()));
        //remove the item
        onView(withId(R.id.wish_list)).perform(ViewActions.swipeLeft());

        //goes back to shuffle page again
        pressBack();
        onView(withId(R.id.action_shuffle)).perform(ViewActions.click());
        //like by swipe right
        onView(withId(R.id.sp_image)).perform(ViewActions.swipeRight());
        //goes to wish list page
        pressBack();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Wish List")).perform(ViewActions.click());
        // then there should be one item
        onData(instanceOf(SnackItem.class)).inAdapterView(withId(R.id.wish_list)).atPosition(0).check(matches(isDisplayed()));
        //remove the item
        onView(withId(R.id.wish_list)).perform(ViewActions.swipeLeft());

        //goes back to shuffle page again
        pressBack();
        onView(withId(R.id.action_shuffle)).perform(ViewActions.click());
        //ignore by swipe left
        onView(withId(R.id.sp_image)).perform(ViewActions.swipeLeft());
        //goes to wish list page
        pressBack();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Wish List")).perform(ViewActions.click());
        // then there should be no item
        onView(withId(R.id.wish_list_item_img)).check(ViewAssertions.doesNotExist());

        //goes back to shuffle page again
        pressBack();
        onView(withId(R.id.action_shuffle)).perform(ViewActions.click());
        //ignore by clicking the button
        onView(withId(R.id.trash_button)).perform(ViewActions.click());
        //goes to wish list page
        pressBack();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Wish List")).perform(ViewActions.click());
        // then there should be no item
        onView(withId(R.id.wish_list_item_img)).check(ViewAssertions.doesNotExist());
    }
}
