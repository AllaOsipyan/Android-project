package com.example.project;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.AmbiguousViewMatcherException;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewFinder;

import com.example.project.View.MainAccountActivity;
import com.google.android.material.textfield.TextInputLayout;

import junit.framework.AssertionFailedError;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class CustomMatcher {
    public static Matcher<View> hasTextInputLayoutErrorText(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextInputLayout)) {
                    return false;
                }

                CharSequence error = ((TextInputLayout) view).getError();

                if (error == null) {
                    return false;
                }

                String hint = error.toString();

                return expectedErrorText.equals(hint);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }

    public static ViewAssertion childViewElementsExist(final Matcher<View> viewHolderMatcher) {
        return new ViewAssertion() {

            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (!(view instanceof RecyclerView)) {
                    throw noViewFoundException;
                }

                RecyclerView rv = (RecyclerView) view;
                if(rv.getChildCount() < 0)
                    throw new AssertionFailedError("Requested item should exist.");

                for (int i = 0; i < rv.getChildCount(); i++) {
                    RecyclerView.ViewHolder vh = rv.findViewHolderForAdapterPosition(i);
                    Assert.assertTrue(viewHolderMatcher.matches(vh.itemView.findViewById(R.id.card_title)));
                    Assert.assertTrue(viewHolderMatcher.matches(vh.itemView.findViewById(R.id.card_book_authors)));
                }

            }


        };
    }


}
