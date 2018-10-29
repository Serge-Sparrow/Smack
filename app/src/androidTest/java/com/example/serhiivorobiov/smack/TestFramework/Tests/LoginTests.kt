package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.TestFramework.Screens.CreateUserScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class LoginTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun waiterBringMenu() {
        Espresso.onView(withContentDescription("Open navigation drawer"))
            .perform(click())
        Espresso.onView(withId(R.id.user_image_nav_header))
            .check(matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.login_btn_nav_header))
            .perform(click())
    }

}