package com.example.serhiivorobiov.smack.TestFramework.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import com.example.serhiivorobiov.smack.Adapters.MessageAdapter
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.MessageService
import com.example.serhiivorobiov.smack.TestFramework.Utilities.MESSAGE_TEXT
import org.hamcrest.CoreMatchers.allOf

class ChatScreen : BaseScreen() {

    override val uniqueView: ViewInteraction
        get() = onView(withContentDescription("Open navigation drawer"))

    private val message = onView(withId(R.id.message_text_field))!!
    private val messageSendBtn = onView(withId(R.id.send_image_btn))
    val messagesInChannel = onView(withId(R.id.message_list_view))
    private var messageBodyHolder = ""

    fun clickOnMessageSendBtn() {
        messageSendBtn.perform(click())
    }

    fun onBurgerClick(): ChannelScreen {

        uniqueView.perform(click())
        return ChannelScreen()
    }

    init {
        uniqueView.check(matches(isDisplayed()))
    }

    fun checkChannelNameHeader(nameOfChannel: String) {
        onView(allOf(withId(R.id.main_channel_name), withText("#$nameOfChannel")))
            .check(matches(isDisplayed()))
    }

    fun checkMessageIsDisplayed() {
        messagesInChannel
                .check(matches(hasDescendant(withText(messageBodyHolder))))
    }

    fun createNewMessage() {
        messageBodyHolder = if (MessageService.messages.size > 0) {
            "$MESSAGE_TEXT${MessageService.messages.size + 1}"
        } else {
            "$MESSAGE_TEXT 1"
        }
        message.perform(replaceText(messageBodyHolder))
    }

    fun scrollMessages() {

        for ( message in MessageService.messages.size - 1 downTo 0) {
            messagesInChannel.perform(RecyclerViewActions
                .scrollToPosition<MessageAdapter.ViewHolder>(message))
        }

        for ( message in 0 until MessageService.messages.size) {
            messagesInChannel.perform(RecyclerViewActions
                .scrollToPosition<MessageAdapter.ViewHolder>(message))
        }
    }
}