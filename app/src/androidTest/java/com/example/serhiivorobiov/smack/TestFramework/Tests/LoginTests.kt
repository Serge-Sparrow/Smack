package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun waiterBringMenu() {
        Espresso.onView(ViewMatchers.withContentDescription("Open navigation drawer"))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.user_image_nav_header))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.login_btn_nav_header))
            .perform(ViewActions.click())
    }

    @Test
    fun anotherSimpleTest() {
        Espresso.onView(ViewMatchers.withContentDescription("Open navigation drawer"))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.user_image_nav_header))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.login_btn_nav_header))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.user_create_btn))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}