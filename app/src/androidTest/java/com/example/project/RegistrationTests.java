package com.example.project;

import android.app.Activity;
import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.project.View.MainAccountActivity;
import com.example.project.View.RegistrationActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.project.CustomMatcher.hasTextInputLayoutErrorText;
import static org.hamcrest.core.AnyOf.anyOf;

@RunWith(AndroidJUnit4.class)
public class RegistrationTests {

    @Rule
    public ActivityTestRule<RegistrationActivity> registrationActivityTestRule = new ActivityTestRule<>(RegistrationActivity.class);

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
    //регистрация с некорректными данными
    @Test
    public void wrongRegistration(){
        onView(withId(R.id.username_reg_text)).perform(typeText("adm"));
        onView(withId(R.id.email_reg_text)).perform(typeText("adm"));
        onView(withId(R.id.password_reg_text)).perform(typeText("123456"));
        onView(withId(R.id.confirm_pass_reg_text)).perform(typeText("12345678"));
        onView(withId(R.id.outlinedButton)).perform(scrollTo(),click());
        //проверяем вывод ошибок
        onView(withId(R.id.username_reg)).check(matches(hasTextInputLayoutErrorText("Кол-во символов не меньше 5")));
        onView(withId(R.id.email_reg)).check(matches(hasTextInputLayoutErrorText("Некорректный адрес")));
        onView(withId(R.id.password_reg)).check(matches(hasTextInputLayoutErrorText("Кол-во символов не меньше 8")));
        onView(withId(R.id.confirm_pass_reg)).check(matches(hasTextInputLayoutErrorText("Пароли не совпадают")));
        onView(withId(R.id.outlinedButton)).check(matches(isDisplayed()));
    }

    //регистрация с пустыми полями
    @Test
    public void emptyFieldsRegistration(){
        closeSoftKeyboard();
        onView(withId(R.id.outlinedButton)).perform(scrollTo(),click());
        //проверяем вывод ошибок
        onView(withId(R.id.error_tv)).check(matches(withText("Все поля должны быть заполнены")));
        onView(withId(R.id.outlinedButton)).check(matches(isDisplayed()));
    }

    //корректные данные
    @Test
    public void registration(){//проверяем, что при успешной регистрации пользователь попадает на страницу поиска
        onView(withId(R.id.username_reg_text)).perform(typeText("newUser"));
        onView(withId(R.id.email_reg_text)).perform(typeText("user@in"));
        onView(withId(R.id.password_reg_text)).perform(typeText("12345678"));
        onView(withId(R.id.confirm_pass_reg_text)).perform(typeText("12345678"));
        onView(withId(R.id.outlinedButton)).perform(scrollTo(),click());
        onView(withId(R.id.search_view_text)).perform(scrollTo(), click()).check(matches(isDisplayed()));
    }


}
