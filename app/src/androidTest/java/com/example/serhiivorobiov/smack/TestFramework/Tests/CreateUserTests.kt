package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.DeleteUserService
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.TestFramework.Screens.CreateUserScreen
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class CreateUserTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun logout() {
        UserDataService.logout()
    }

    @After
    fun deleteCreatedUser() {
        val userName = UserDataService.name
        val userId = UserDataService.id
        if (UserDataService.id != "") {
            DeleteUserService.deleteUser(userName, userId) { _ ->
                println("User deleted successfully ololo")
                sleep(2500)
            }
        } else println("ololo")
    }

    @Test
    fun anotherSimpleTest() {
        onView(withContentDescription("Open navigation drawer"))
            .perform(click())
        onView(withId(R.id.login_btn_nav_header))
            .perform(ViewActions.click())
        onView(withId(R.id.user_create_btn)).perform(click())
        val createScr = CreateUserScreen()
        createScr.onClickCreateUSerButton(1)
        sleep(3000)

    }

    @Test
    fun anotherErrorSimpleTest() {
        onView(withContentDescription("Open navigation drawer"))
            .perform(click())
        onView(withId(R.id.login_btn_nav_header))
            .perform(click())
        onView(withId(R.id.user_create_btn)).perform(click())
        val createScr = CreateUserScreen()
        createScr.setUserName()
        sleep(500)
        createScr.onClickCreateUSerButton(0)
        sleep(500)

    }
}