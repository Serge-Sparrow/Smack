package com.example.serhiivorobiov.smack.TestFramework.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.example.serhiivorobiov.smack.R

class ChatScreen : BaseScreen() {

    override val uniqueView: ViewInteraction
        get() = onView(withContentDescription("Open navigation drawer"))

    val loginStatusText = onView(withId(R.id.main_channel_name))
    val message = onView(withId(R.id.message_text_field))
    private val messageSendBtn = onView(withId(R.id.send_image_btn))

    fun clickOnMessageSendBtn() {
        messageSendBtn.perform(click())
    }

    fun onBurgerClick(): BaseScreen {

        uniqueView.perform(click())
        return ChannelScreen()
    }

    init {
        uniqueView.check(matches(isDisplayed()))
    }
}