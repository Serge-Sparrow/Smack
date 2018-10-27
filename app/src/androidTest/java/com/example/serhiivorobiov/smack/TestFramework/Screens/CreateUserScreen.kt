package com.example.serhiivorobiov.smack.TestFramework.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.TestFramework.Utilities.USER_NAME
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_PASSWORD
import java.util.Random

class CreateUserScreen : BaseScreen() {

    override val uniqueView: ViewInteraction
        get() = onView(withId(R.id.create_user_btn))

    private val userName = onView(withId(R.id.create_act_user_name_text))
    private val userEmail  = onView(withId(R.id.create_act_email_text))
    private val userPass  = onView(withId(R.id.create_act_password_text))
    private val avatar = onView(withId(R.id.create_act_avatar_view))
    private val backgroundColor = onView(withId(R.id.background_color_btn))

    fun onClickCreateUSerButton(typeOfCreation: Int): BaseScreen {
        return when (typeOfCreation) {
            //Create user and account
            2 -> {
                setAllTextFields(USER_NAME, randomSetUserEmail(), randomUserPassword())
                clickOnAvatarImage()
                onClickBackground()

                uniqueView.perform(click())
                ChannelScreen()
            }
            //Create user using existing account
            1 -> {
                setAllTextFields(USER_NAME, VALID_EMAIL, VALID_PASSWORD)
                clickOnAvatarImage()
                onClickBackground()

                uniqueView.perform(click())
                ChannelScreen()
            }
            else -> {
                uniqueView.perform(click())
                UserDataService.id = ""
                this
            }
        }
    }

    fun onClickBackground() {

        backgroundColor.perform(click())
    }

    fun randomSetUserEmail(): String {

        val ran = Random()
        val char = 'a'
        fun randomChar() : Char = (ran.nextInt(26) + char.toInt()).toChar()
        return ("${randomChar()}${randomChar()}${randomChar()}${randomChar()}" + "@example.com")
    }

    fun randomUserPassword(): String {
        val ran = Random()
        return "${ran.nextInt(1000000000)}"
    }

    fun clickOnAvatarImage() {
        avatar.perform(click())
    }

    fun setAllTextFields (name: String, email: String, pass: String) {
        userName.perform(replaceText(name))
        userEmail.perform(replaceText(email))
        userPass.perform(replaceText(pass))
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