package com.example.serhiivorobiov.smack.TestFramework.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.TestFramework.Utilities.getText
import org.hamcrest.CoreMatchers
import java.util.concurrent.atomic.AtomicReference

class ChannelScreen: BaseScreen() {

    override val uniqueView: ViewInteraction
        get() = onView(withId(R.id.add_channel_btn))

    private val loginBtn = onView(withId(R.id.login_btn_nav_header))
    private val userEmail = onView(withId(R.id.user_email_nav_header))
    private val userName = onView(withId(R.id.user_name_nav_header))

    private val toast:ViewInteraction? = onView(
        ViewMatchers.withText("Please Login!")
    )

    fun checkIsToastDisplayed( rule: ActivityTestRule<MainActivity>) {
        toast?.inRoot(RootMatchers.withDecorView(CoreMatchers.not(CoreMatchers.`is`(rule.activity.window.decorView))))
            ?.check(matches(isDisplayed()))
    }

    private val loginBtnText: String
        get() {
            val holder = AtomicReference<String>()
            loginBtn.perform(getText(holder))
            return holder.get()
        }

    val userNameTxt: String
    get() {
        val holder = AtomicReference<String>()
        userName.perform(getText(holder))
        return holder.get()
    }

    val userEmailTxt: String
        get() {
            val holder = AtomicReference<String>()
            userEmail.perform(getText(holder))
            return holder.get()
        }

    fun onLoginBtnClick(): BaseScreen {

       return when (loginBtnText) {

            "Login" -> {
                loginBtn.perform(click())
                return LoginScreen()
            }
            "Logout" -> {
                loginBtn.perform(click())
                return this
            }
            else ->  this
        }
    }

    fun clickOnAddChannelBtn() {

       uniqueView.perform(click())
    }

    init {
        uniqueView.check(matches(isDisplayed()))
    }
}