package com.example.serhiivorobiov.smack.TestFramework.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.TestFramework.Utilities.INVALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.INVALID_PASSWORD
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_PASSWORD
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not

class LoginScreen: BaseScreen() {

    override val uniqueView: ViewInteraction = onView(withId(R.id.text_login_screen))
    private val signUpButton = onView(withId(R.id.user_create_btn))
    private val loginButton = onView(withId(R.id.log_act_login_btn))
    private val userEmail = onView(withId(R.id.login_email_text))
    private val userPass = onView(withId(R.id.login_password_text))
    val progressBar = onView(withId(R.id.login_spinner))
    val toastValidation:ViewInteraction? = onView(
        ViewMatchers.withText("Please, make sure email and/or password are filled in!")
    )
    val toastIllegalLogin:ViewInteraction? = onView(
        ViewMatchers.withText("Something goes wrong, please try again.")
    )

    fun checkIsToastDisplayed( rule: ActivityTestRule<MainActivity>, toastType: ViewInteraction?) {
        toastType?.inRoot(withDecorView(not(`is`(rule.activity.window.decorView))))
            ?.check(matches(isDisplayed()))
    }
    init {
        uniqueView.check(matches(isDisplayed()))
    }

    fun clickOnLogInButton(validLog: Int, email: String, password: String?): BaseScreen {
        return when (validLog) {
            2 -> {
                setUserEmail(email)
                setUserPassword(password!!)
                loginButton.perform(click())
                this
            }
            1 -> {
                setUserEmail(email)
                setUserPassword(password!!)
                loginButton.perform(click())
                ChannelScreen()
            }
            else -> {
                setUserEmail(email)
                loginButton.perform(click())
                 this
            }
        }
    }

    fun clickOnSignUpButton(): CreateUserScreen {

        signUpButton.perform(click())
        return CreateUserScreen()
    }

    private fun setUserEmail(email: String) {

        userEmail.perform(replaceText(email))
    }

    private fun setUserPassword(password: String) {

        userPass.perform(replaceText(password))
    }
}