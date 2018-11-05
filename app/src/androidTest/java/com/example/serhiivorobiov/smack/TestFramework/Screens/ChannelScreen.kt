package com.example.serhiivorobiov.smack.TestFramework.Screens

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.example.serhiivorobiov.smack.Adapters.ChannelAdapter
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.MessageService
import com.example.serhiivorobiov.smack.TestFramework.Utilities.clickDeleteChannel
import com.example.serhiivorobiov.smack.TestFramework.Utilities.getChannelName
import com.example.serhiivorobiov.smack.TestFramework.Utilities.getText
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import java.util.concurrent.atomic.AtomicReference

class ChannelScreen : BaseScreen() {
    var channelNameHolder: String = ""

    override val uniqueView: ViewInteraction
        get() = onView(withId(R.id.add_channel_btn))

    val loginBtn = onView(withId(R.id.login_btn_nav_header))
    private val userEmail = onView(withId(R.id.user_email_nav_header))
    private val userName = onView(withId(R.id.user_name_nav_header))
    private val addChannelName = onView(withId(R.id.add_channel_name))
    private val addChannelDescriptions = onView(withId(R.id.add_channel_disc))
    private val addBtn = onView(withText("Add"))
    private val toast: ViewInteraction? = onView(ViewMatchers.withText("Please Login!"))
    val drawer = onView(withId(R.id.drawer_layout))
    val channelList = onView(withId(R.id.channel_list))

    var lastChannel = fun (): Int {
        return if (MessageService.channels.size > 0) {
            MessageService.channels.indexOf(MessageService.channels.last())
        } else 0
    }

    fun checkIsToastDisplayed(rule: ActivityTestRule<MainActivity>) {
        toast?.inRoot(RootMatchers.withDecorView(not(`is`(rule.activity.window.decorView))))
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
            else -> {
                loginBtn.perform(click())
                this
            }
        }
    }

    fun clickOnAddChannelBtn() {

        uniqueView.perform(click())
    }

    fun addNewChannel() {
        channelNameHolder = "TestChannel- ${MessageService.channels.size + 1}"
        clickOnAddChannelBtn()
        addChannelName.perform(replaceText(channelNameHolder))
        addChannelDescriptions.perform(replaceText("Channel for testing"))
        addBtn.perform(click())
    }

    fun clickOnChannel(position: Int) {
    channelList.perform(RecyclerViewActions.actionOnItemAtPosition<ChannelAdapter.ViewHolder>(position,
        click()))
    }

    fun deleteChannelAtPosition(position: Int) {

        channelList.perform(RecyclerViewActions
            .actionOnItemAtPosition<ChannelAdapter.ViewHolder>(position, clickDeleteChannel()))
    }

    fun scrollToChannel(position: Int) {

        channelList.perform(RecyclerViewActions
            .scrollToPosition<ChannelAdapter.ViewHolder>(position))
    }

    fun checkIsChannelDisplayed(name: String) {
        onView(withText(name)).check(matches(isDisplayed()))
    }

    fun checkIsChannelDeleted(name: String) {
        onView(withText(name)).check(doesNotExist())
    }

    val channelName = fun (position: Int): String {

            val holder = AtomicReference<String>()
        channelList.perform(RecyclerViewActions
            .actionOnItemAtPosition<ChannelAdapter.ViewHolder>(position, getChannelName(holder)))
        return holder.get().toString()
        }

    fun deleteAllChannels() {

        while (MessageService.channels.isNotEmpty()) {
            deleteChannelAtPosition(0)
        }
    }

    init {

        uniqueView.check(matches(isDisplayed()))
    }
}