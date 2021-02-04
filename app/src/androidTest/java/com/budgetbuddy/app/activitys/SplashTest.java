package com.budgetbuddy.app.activitys;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.budgetbuddy.app.R;
import com.budgetbuddy.app.databases.Budgets;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SplashTest {

    @Rule
    public ActivityTestRule<Splash> mActivityTestRule = new ActivityTestRule<>(Splash.class);

    @BeforeClass
    public static void beforeClass(){
        //clear database before every test
        InstrumentationRegistry.getTargetContext().deleteDatabase(Budgets.DATABASE_NAME);
    }
    @Test
    public void splashTest() {
        //get button named "Create your budget"
        ViewInteraction materialButton = onView(
                allOf(withText("Create your budget"),
                        isDisplayed()));
        //click button
        materialButton.perform(click());

        //get "Done" button
        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.cratebucket_bnv_menu_done),
                        isDisplayed()));
        //check button named "Done" is displayed in the bottom navigation menu
        frameLayout.check(matches(isDisplayed()));

        //get button named "Add Category"
        ViewInteraction frameLayout2 = onView(
                allOf(withId(R.id.cratebucket_bnv_menu_addcategory),
                        isDisplayed()));
        //check button named "Add Category" is displayed in the bottom navigation menu
        frameLayout2.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
