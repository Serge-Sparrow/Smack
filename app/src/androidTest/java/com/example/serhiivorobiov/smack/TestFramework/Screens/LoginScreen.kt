package com.example.serhiivorobiov.smack.TestFramework.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.example.serhiivorobiov.smack.R

class LoginScreen: BaseScreen() {

    override val uniqueView: ViewInteraction = onView(withId(R.id.text_login_screen))
    private val signUpButton = onView(withId(R.id.user_create_btn))
    private val loginButton = onView(withId(R.id.log_act_login_btn))
    private val userEmail = onView(withId(R.id.login_email_text))
    private val userPass = onView(withId(R.id.login_password_text))

    init {
        uniqueView.check(matches(isDisplayed()))
    }

    fun clickOnLogInButton(validLog: Int): BaseScreen {
        return when (validLog) {
            1 -> {
                loginButton.perform(click())
                ChannelScreen()
            }
            else -> {
                loginButton.perform(click())
                 this
            }
        }
    }

    fun clickOnSignUpButton(): CreateUserScreen {
        signUpButton.perform(click())
        return CreateUserScreen()
    }

    fun setUserEmail(email: String) {

        userEmail.perform(replaceText(email))
    }

    fun setUserPAssword(password: String) {

        userPass.perform(replaceText(password))
    }
}