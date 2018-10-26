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
import java.util.Random

class CreateUserScreen : BaseScreen() {

    override val uniqueView: ViewInteraction
        get() = onView(withId(R.id.create_user_btn))

    private val userName = onView(withId(R.id.create_act_user_name_text))
    private val userEmail  = onView(withId(R.id.create_act_email_text))
    private val userPass  = onView(withId(R.id.create_act_password_text))
    private val avatar = onView(withId(R.id.create_act_avatar_view))
    private val backgroundColor = onView(withId(R.id.background_color_btn))

    fun onClickCreateUSerButton(validLog: Int): BaseScreen {
        return when (validLog) {
            1 -> {
                setUserName()
                setUserEmail()
                setUserPassword()
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

    fun setUserName() {
        val ran = Random()
        val char = 'a'
        fun randomChar() : Char = (ran.nextInt(26) + char.toInt()).toChar()
        val name = "${randomChar()}${randomChar()}${randomChar()}${randomChar()}${randomChar()}${randomChar()}"
        userName.perform(replaceText(name))
    }

    fun setUserEmail() {

        val ran = Random()
        val char = 'a'
        fun randomChar() : Char = (ran.nextInt(26) + char.toInt()).toChar()
        val email = "${randomChar()}${randomChar()}${randomChar()}${randomChar()}" + "@example.com"
        userEmail.perform(replaceText(email))
    }

    fun setUserPassword() {
        val ran = Random()
        val password = "${ran.nextInt(1000000000)}"
        userPass.perform(replaceText(password))
    }

    fun clickOnAvatarImage() {
        avatar.perform(click())
    }

    init{

        uniqueView.check(matches(isDisplayed()))
    }
}