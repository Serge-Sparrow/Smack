package com.example.serhiivorobiov.smack.TestFramework.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_PASSWORD
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_USER_NAME
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import java.util.Random

class CreateUserScreen : BaseScreen() {

    override val uniqueView: ViewInteraction
        get() = onView(withId(R.id.create_user_btn))

    private val userName = onView(withId(R.id.create_act_user_name_text))
    private val userEmail  = onView(withId(R.id.create_act_email_text))
    private val userPass  = onView(withId(R.id.create_act_password_text))
    private val avatar: ViewInteraction? = onView(withId(R.id.create_act_avatar_view))
    private val backgroundColor = onView(withId(R.id.background_color_btn))

    private val toast:ViewInteraction? = onView(
        withText("Please, make sure user name, email and/or password are filled in")
        )

    fun checkIsToastDisplayed( rule: ActivityTestRule<MainActivity>) {
        toast?.inRoot(withDecorView(not(`is`(rule.activity.window.decorView))))
            ?.check(matches(isDisplayed()))
    }

    fun onClickCreateUserButton(typeOfCreation: Int): BaseScreen {
        return when (typeOfCreation) {
            //Create user and account
            2 -> {
                setAllTextFields(VALID_USER_NAME, randomSetUserEmail(), randomUserPassword())
                onClickBackground()
                clickOnAvatarImage()

                uniqueView.perform(click())
                ChannelScreen()
            }
            //Create user using existing account
            1 -> {
                setAllTextFields(VALID_USER_NAME, VALID_EMAIL, VALID_PASSWORD)
                onClickBackground()
                clickOnAvatarImage()

                uniqueView.perform(click())
                ChannelScreen()
            }

            else -> {
                uniqueView.perform(click())
                UserDataService.id = ""
                setAllTextFields(VALID_USER_NAME,null, null)
                this
            }
        }
    }

   private fun onClickBackground() {

        backgroundColor.perform(click())
    }

   private fun randomSetUserEmail(): String {

        val ran = Random()
        val char = 'a'
        fun randomChar(): Char = (ran.nextInt(26) + char.toInt()).toChar()
        return ("${randomChar()}${randomChar()}${randomChar()}${randomChar()}" + "@example.com")
    }

    private fun randomUserPassword(): String {
        val ran = Random()
        return "${ran.nextInt(1000000000)}"
    }

   private fun clickOnAvatarImage() {
       avatar?.perform(click())
    }

    private fun setAllTextFields(name: String?, email: String?, pass: String?) {
        if (name != null) {
            userName.perform(replaceText(name))
        }
        if (email != null) {
            userEmail.perform(replaceText(email))
        }
        if (pass != null) {
            userPass.perform(replaceText(pass))
        }
    }

    init{
        uniqueView.check(matches(isDisplayed()))
    }

    //    fun randomSetUserName(): String {
//        val ran = Random()
//        val char = 'a'
//        fun randomChar() : Char = (ran.nextInt(26) + char.toInt()).toChar()
//        return ("${randomChar()}${randomChar()}${randomChar()}${randomChar()}${randomChar()}${randomChar()}")
//    }

}