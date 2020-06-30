package com.example.project;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.project.View.MainAccountActivity;
import com.example.project.View.MainActivity;
import com.example.project.View.RegistrationActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class MainAccountActivityTests {
    @Rule
    public ActivityTestRule<MainAccountActivity> mainAccountActivityTestRule = new ActivityTestRule<>(MainAccountActivity.class);

    @Before
    public void exit(){
        final Activity[] activity = new Activity[1];
        onView(isRoot()).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                activity[0] = (Activity) view.getContext();
            }
        });
        if(activity[0] instanceof MainActivity){
            onView(withId(R.id.auth)).perform(click());
            onView(withId(R.id.username_text)).perform(typeText("admin"));
            onView(withId(R.id.password_text)).perform(typeText("12345678"));
            onView(withId(android.R.id.button1)).perform(click());
        }
    }
    //Проверка полученных данных
    @Test
    public void booksExist() throws InterruptedException {
        onView(withId(R.id.recycler_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.search_book)).perform(typeText("river")).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        Thread.sleep(5000);
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).check(CustomMatcher.childViewElementsExist(allOf(isDisplayed(), not(withText("")))));
    }

}
