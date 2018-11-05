package com.example.serhiivorobiov.smack.TestFramework.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.example.serhiivorobiov.smack.Adapters.ChannelAdapter
import com.example.serhiivorobiov.smack.Adapters.MessageAdapter
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.MessageService
import com.example.serhiivorobiov.smack.TestFramework.Utilities.MESSAGE_TEXT
import com.example.serhiivorobiov.smack.TestFramework.Utilities.clickDeleteMessage
import com.example.serhiivorobiov.smack.TestFramework.Utilities.getTextHint
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import java.util.concurrent.atomic.AtomicReference

class ChatScreen : BaseScreen() {

    override val uniqueView: ViewInteraction
        get() = onView(withContentDescription("Open navigation drawer"))

    val message = onView(withId(R.id.message_text_field))!!
    private val messageSendBtn = onView(withId(R.id.send_image_btn))
    val messagesInChannel = onView(withId(R.id.message_list_view))
    private var messageBodyHolder = ""

    val toastNoChannelSelected: ViewInteraction? = onView(
        ViewMatchers.withText("Please, create or select channel first!")
    )
    val toastNoMessageBody: ViewInteraction? = onView(
        withText("Please, type message first!")
    )

    val snackNotLogIn: ViewInteraction? = onView(
        withText("Please, log in first!")
    )

    val snackLogInAction: ViewInteraction? = onView(
        allOf(withText("Login"), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))
    )

    fun checkIsToastDisplayed(rule: ActivityTestRule<MainActivity>, toastType: ViewInteraction?) {
        toastType?.inRoot(withDecorView(not(`is`(rule.activity.window.decorView))))
            ?.check(matches(isDisplayed()))
    }

    fun clickOnMessageSendBtn() {
        messageSendBtn.perform(click())
    }

    val messageHint: String
        get() {
            val holder = AtomicReference<String>()
            message.perform(getTextHint(holder))
            return holder.get()
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

    fun deleteMessageAtPosition(position: Int) {

        messagesInChannel.perform(RecyclerViewActions
            .actionOnItemAtPosition<ChannelAdapter.ViewHolder>(position, clickDeleteMessage()))
    }

    fun scrollMessages() {
        if (MessageService.messages.size > 1) {
            for (message in MessageService.messages.size - 1 downTo 0) {
                messagesInChannel.perform(
                    RecyclerViewActions
                        .scrollToPosition<MessageAdapter.ViewHolder>(message)
                )
            }

            for (message in 0 until MessageService.messages.size) {
                messagesInChannel.perform(
                    RecyclerViewActions
                        .scrollToPosition<MessageAdapter.ViewHolder>(message)
                )
            }
        }
    }
}