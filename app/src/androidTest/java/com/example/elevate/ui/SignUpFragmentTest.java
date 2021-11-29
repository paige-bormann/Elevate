package com.example.elevate.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;

import com.example.elevate.R;

import junit.framework.TestCase;

import org.junit.Test;

public class SignUpFragmentTest extends TestCase {

    @Test
    public void testLoginFragNavigation(){
        //setup
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        //navigate to sign in screen
        onView(withId(R.id.sign_up_button)).perform(click());

        //verify we are in fragment_login
        onView(withId(R.id.skill_level_textview)).check(matches(isDisplayed()));
    }
}