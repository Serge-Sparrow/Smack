package com.example.serhiivorobiov.smack.TestFramework.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription

class ChatScreen : BaseScreen() {

    override val uniqueView: ViewInteraction
        get() = onView(withContentDescription("Open navigation drawer"))

    fun onBurgerClick(): BaseScreen {

        uniqueView.perform(click())
        return ChannelScreen()
    }

    init {
        uniqueView.check(matches(isDisplayed()))
    }
}