package com.mogowebdesign.weatherapplication;

import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.view.View;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.fail;

/**
 * Created by rijogeorge on 2/11/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);



    @BeforeClass
    public static void setUpClass() {
        System.out.println("Master setup");


    }

    @Test
    public void testGUI() {

        onView(withId(R.id.my_recycler_view_hour)).check(matches(isDisplayed()));
        onView(withId(R.id.my_recycler_view_dayily)).check(matches(isDisplayed()));
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Master tearDown");
    }


}
