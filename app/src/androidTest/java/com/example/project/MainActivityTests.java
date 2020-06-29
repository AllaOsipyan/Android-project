package com.example.project;

import android.app.Activity;
import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.project.View.MainAccountActivity;
import com.example.project.View.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTests {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Before
    public void exit(){
        final Activity[] activity = new Activity[1];
        onView(isRoot()).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                activity[0] = (Activity) view.getContext();
            }
        });
        if(activity[0] instanceof MainAccountActivity){
            onView(withId(R.id.acc_exit)).perform(click());
        }
    }
    @Test
    public void newBook() throws InterruptedException {
        onView(withId(R.id.book_title)).check(matches(withText("")));
        onView(withId(R.id.outlinedButton)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.book_title)).check(matches(not(withText(""))));
        onView(withId(R.id.book_image)).check(matches(isDisplayed()));
    }
}
