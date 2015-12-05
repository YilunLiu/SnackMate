package com.hexad.snackmate;

import android.app.Application;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.test.ApplicationTestCase;

/**
 * Created by Pin Wang on 12/3/15.
 */
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.hexad.snackmate.Activities.HomePageActivity;
import com.hexad.snackmate.Models.SnackItem;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PaymentActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

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
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.*;
import static org.hamcrest.Matchers.*;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CartTest {

    @Rule
    public ActivityTestRule<HomePageActivity> mActivityRule = new ActivityTestRule<>(HomePageActivity.class);


    @Test
    public void Checkout() throws InterruptedException {

        // click to go to item detail
        onData(anything()).inAdapterView(withId(R.id.homepage_gridview)).atPosition(0).perform(ViewActions.click());
        // add to shopping cart
        onView(withId(R.id.item_detail_cart)).perform(ViewActions.click());
        // goes to shopping cart page
        pressBack();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Shopping Cart")).perform(ViewActions.click());
        onView(withId(R.id.buyItBtn)).perform(ViewActions.click());
        TimeUnit.SECONDS.sleep(1);
        onView(withText("Clear card information")).perform(ViewActions.click());
        onView(withText("OK")).perform(ViewActions.click());
        onView(withText("Pay with Card")).perform(ViewActions.click());
        onView(withId(100)).perform(ViewActions.typeText("4266471878970981"));
        onView(withId(101)).perform(ViewActions.typeText("1118"));
        onView(withId(102)).perform(ViewActions.typeText("520"));
        onView(withText("Done")).perform(ViewActions.click());
        onView(withText("Charge Card")).perform(ViewActions.click());
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.cart)).check(doesNotExist());
    }

}
