package com.example.serhiivorobiov.smack.TestFramework


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.R
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class WaiterTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)



    @Test
    fun waiterBringMenu() {
       onView(withContentDescription("Open navigation drawer"))
           .perform(click())
        onView(withId(R.id.user_image_nav_header))
            .check(matches(isDisplayed()))
            onView(withId(R.id.login_btn_nav_header))
                .perform(click())
    }

    @Test
    fun anotherSimpleTest() {
        onView(withContentDescription("Open navigation drawer"))
            .perform(click())
        onView(withId(R.id.user_image_nav_header))
            .check(matches(isDisplayed()))
        onView(withId(R.id.login_btn_nav_header))
            .perform(click())
            onView(withId(R.id.user_create_btn))
                .check(matches(isDisplayed()))
    }
}