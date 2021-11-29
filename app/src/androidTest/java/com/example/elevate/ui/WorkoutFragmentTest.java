package com.example.elevate.ui;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;

import com.example.elevate.R;
import com.example.elevate.ui.MainActivity;

import junit.framework.TestCase;

import org.junit.Test;


public class WorkoutFragmentTest extends TestCase {

    @Test
    public void testClimbingPlanFragNavigation(){
        //setup
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        //navigate to sign in screen
        onView(withId(R.id.sign_in_button)).perform(click());

        //navigate through sign in screen
        onView(withId(R.id.username_text)).perform(typeText("tuttle2"));
        onView(withId(R.id.password_text)).perform(typeText("tut2"));
        onView(withId(R.id.survey_button)).perform(click());

        //navigate to workout screen
        onView(withId(R.id.climbing_plan_button)).perform(click());

        //verify we are in fragment_workout
        onView(withId(R.id.climbing_plan_recyclerview)).check(matches(isDisplayed()));
    }
  
}