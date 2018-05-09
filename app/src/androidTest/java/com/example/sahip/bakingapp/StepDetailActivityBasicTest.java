package com.example.sahip.bakingapp;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class StepDetailActivityBasicTest {
    public static final String NEXT_STEP = "Step 2";
    public static final String PREVIUOUS_STEP = "Step 1";


    @Rule
    public ActivityTestRule<StepDetailActivity> activityActivityTestRule =
            new ActivityTestRule<>(StepDetailActivity.class, false, false);

    @Test
    public void clickNextButton_changesToNextStep(){

        // Set position extra
        Intent starterIntent = new Intent();
        starterIntent.putExtra(StepDetailActivity.POSITION, 1);
        activityActivityTestRule.launchActivity(starterIntent);

        // Click on the next button
        onView((withId(R.id.step_next_btn)))
                .perform(scrollTo())
                .perform(click());

        // Check if it changes to the next step
        matchToolbarTitle(NEXT_STEP)
                .check(matches(isDisplayed()));

    }

    @Test
    public void clickPreviousButton_changesToPreviousStep(){
        // Set position extra
        Intent starterIntent = new Intent();
        starterIntent.putExtra(StepDetailActivity.POSITION, 2);
        activityActivityTestRule.launchActivity(starterIntent);

        // Click on the previous button
        onView((withId(R.id.step_prev_btn)))
                .perform(scrollTo())
                .perform(click());

        // Check if it changes to the previous step
        matchToolbarTitle(PREVIUOUS_STEP)
                .check(matches(isDisplayed()));


    }


    private static ViewInteraction matchToolbarTitle(
            CharSequence title) {
        return onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(title))));
    }

    private static Matcher<Object> withToolbarTitle(
            final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }
            @Override public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }
}
