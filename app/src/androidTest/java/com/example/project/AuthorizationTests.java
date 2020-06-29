package com.example.project;

import android.app.Activity;
import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.project.View.MainAccountActivity;
import com.example.project.View.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.project.CustomMatcher.hasTextInputLayoutErrorText;


@RunWith(AndroidJUnit4.class)
public class AuthorizationTests {

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
    public void emptyFieldAuthorization() {
        onView(withId(R.id.auth)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.username)).check(matches(hasTextInputLayoutErrorText("Поле должно быть заполнено")));
        onView(withId(R.id.password)).check(matches(hasTextInputLayoutErrorText("Поле должно быть заполнено")));
    }



    @Test
    public void wrongNameAuthorization() {
        onView(withId(R.id.auth)).perform(click());
        onView(withId(R.id.username_text)).perform(typeText("admins"));
        onView(withId(R.id.password_text)).perform(typeText("12345678"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.username)).check(matches(hasTextInputLayoutErrorText("Имя пользователя введено неправильно")));
    }

    @Test
    public void wrongPassAuthorization() {
        onView(withId(R.id.auth)).perform(click());
        onView(withId(R.id.username_text)).perform(typeText("admin"));
        onView(withId(R.id.password_text)).perform(typeText("1234"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.password)).check(matches(hasTextInputLayoutErrorText("Пароль введен неправильно")));
    }

    @Test
    public void authorization() {
        onView(withId(R.id.auth)).perform(click());
        onView(withId(R.id.username_text)).perform(typeText("admin"));
        onView(withId(R.id.password_text)).perform(typeText("12345678"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.search_view_text)).check(matches(isDisplayed()));
    }
}
