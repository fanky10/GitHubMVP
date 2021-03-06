package com.andevcba.githubmvp.presentation.espresso;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.andevcba.githubmvp.R;
import com.andevcba.githubmvp.presentation.add_repos.AddReposActivity;
import com.andevcba.githubmvp.presentation.show_repos.view.ReposActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * UI tests for {@link AddReposActivity} starting at {@link ReposActivity} using Espresso test recorder.
 *
 * @author lucas.nobile
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class Flow_TapOnSearchForReposButton_WithValidUsername_ShouldShowReposByUsername {

    private final static String USERNAME = "AnDevCba";

    @Rule
    public ActivityTestRule<ReposActivity> mActivityTestRule = new ActivityTestRule<>(ReposActivity.class);

    @Test
    public void tapOnSearchForReposButton_withValidUsername_shouldShowReposByUsername() {
        ViewInteraction floatingActionButton = onView(allOf(withId(R.id.fab_add_repos),
                withParent(withId(R.id.coordinatorLayout)),
                isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.tv_username),
                        withParent(withId(R.id.search_repos_container)),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.tv_username),
                        withParent(withId(R.id.search_repos_container)),
                        isDisplayed()));
        appCompatEditText2.perform(typeText(USERNAME), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_search_repos),
                        withText(R.string.search_repos_button),
                        withParent(withId(R.id.search_repos_container)),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.tv_username),
                        withText(USERNAME),
                        childAtPosition(
                                allOf(withId(R.id.search_repos_container),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                1),
                        isDisplayed()));
        editText.check(matches(withText(USERNAME)));

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.rv_show_repos),
                                0),
                        0),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.rv_show_repos),
                                1),
                        0),
                        isDisplayed()));
        linearLayout2.check(matches(isDisplayed()));
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
