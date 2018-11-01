package com.example.serhiivorobiov.smack.TestFramework.Screens

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions.open
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.view.Gravity.START
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.MessageService
import com.example.serhiivorobiov.smack.TestFramework.Utilities.getText
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.anything
import java.util.Random
import java.util.concurrent.atomic.AtomicReference

class ChannelScreen : BaseScreen() {
    private val ran = Random()
    var channelNameHolder: String = ""

    override val uniqueView: ViewInteraction
        get() = onView(withId(R.id.add_channel_btn))

    private val loginBtn = onView(withId(R.id.login_btn_nav_header))
    private val userEmail = onView(withId(R.id.user_email_nav_header))
    private val userName = onView(withId(R.id.user_name_nav_header))
    private val addChannelName = onView(withId(R.id.add_channel_name))
    private val addChannelDescriptions = onView(withId(R.id.add_channel_disc))
    private val addBtn = onView(withText("Add"))
    private val toast: ViewInteraction? = onView(ViewMatchers.withText("Please Login!"))
    private val message = onView(withId(R.id.message_text_field))!!
    private val messageSendBtn = onView(withId(R.id.send_image_btn))
    private val openDrawer = onView(withId(R.id.drawer_layout))

    var lastChannel = fun (): Int {
        return if (MessageService.channels.size > 0) {
            MessageService.channels.indexOf(MessageService.channels.last())
        } else 0
    }

    fun checkIsToastDisplayed(rule: ActivityTestRule<MainActivity>) {
        toast?.inRoot(RootMatchers.withDecorView(CoreMatchers.not(CoreMatchers.`is`(rule.activity.window.decorView))))
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
        uniqueView.perform(click())
        addChannelName.perform(replaceText(channelNameHolder))
        addChannelDescriptions.perform(replaceText("Channel for testing"))
        addBtn.perform(click())
    }

    fun clickOnChannel(position: Int) {
        onData(anything()).inAdapterView(withId(R.id.channel_list))
            .atPosition(position)
            .perform(click())
    }

    fun clickOnEveryChannelAndTypeHello() {
        if (MessageService.channels.size > 0) {
            for (channel in 0 until MessageService.channels.size) {
                clickOnChannel(channel)
                message.perform(replaceText("Hello =)"))
                messageSendBtn.perform(click())
                openDrawer.perform(open(START))
            }
        }
    }

    init {
        uniqueView.check(matches(isDisplayed()))
    }
}