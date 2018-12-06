package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions.close
import android.support.test.espresso.contrib.DrawerActions.open
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.Services.DeleteAllMessagesService
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.TestFramework.Screens.ChatScreen
import com.example.serhiivorobiov.smack.TestFramework.Screens.LoginScreen
import com.example.serhiivorobiov.smack.TestFramework.Utilities.MESSAGE_HINT
import com.example.serhiivorobiov.smack.TestFramework.Utilities.SECOND_VALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.SECOND_VALID_PASSWORD
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_LOGIN
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_PASSWORD
import com.example.serhiivorobiov.smack.Utilities.IdlingResourceHolding
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun beforeEachTest() {
        IdlingRegistry.getInstance().register(IdlingResourceHolding.idlingResource)
    }

    @Test
    fun sendMessageToNewCreatedChannel() {
        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.addNewChannel()
        channelScreen.clickOnChannel(channelScreen.lastChannel())
        chatScreen.createNewMessage()
        chatScreen.clickOnMessageSendBtn()
        chatScreen.checkMessageIsDisplayed()
        chatScreen.deleteMessageAtPosition(0)
        channelScreen.drawer.perform(open())
        channelScreen.deleteAllChannels()
    }

    @Test
    fun checkSentMessageReceivedByAnotherUser() {
        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.addNewChannel()
        channelScreen.clickOnChannel(channelScreen.lastChannel())
        chatScreen.createNewMessage()
        chatScreen.clickOnMessageSendBtn()
        chatScreen.onBurgerClick()
        channelScreen.onLoginBtnClick()
        channelScreen.onLoginBtnClick()
        loginScreen.clickOnLogInButton(VALID_LOGIN, SECOND_VALID_EMAIL, SECOND_VALID_PASSWORD)
        channelScreen.clickOnChannel(channelScreen.lastChannel())
        chatScreen.checkMessageIsDisplayed()
        chatScreen.deleteMessageAtPosition(0)
        channelScreen.drawer.perform(open())
        channelScreen.deleteAllChannels()
    }

    @Test
    fun checkIfMessagesCouldBeScrolled() {
        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.addNewChannel()
        channelScreen.clickOnChannel(channelScreen.lastChannel())
        for (messagesToAdd in 0..10) {
            chatScreen.createNewMessage()
            chatScreen.clickOnMessageSendBtn()
        }
        chatScreen.scrollMessages()
        channelScreen.drawer.perform(open())
        channelScreen.deleteAllChannels()
        DeleteAllMessagesService.deleteAllMessages { }
    }

    @Test
    fun checkIsMessageTextHintIsCorrect() {

        val chatScreen = ChatScreen()
        assertThat(chatScreen.messageHint, equalTo(MESSAGE_HINT))
    }

    @Test
    fun clickOnSendBtnAndNotLoginAndCheckSnackBarIsDisplayed() {
        val chatScreen = ChatScreen()
        chatScreen.clickOnMessageSendBtn()
        chatScreen.snackNotLogIn?.check(matches(isDisplayed()))
    }

    @Test
    fun clickOnSnackActionAndCheckIfLoginScreenDisplayed() {
        val chatScreen = ChatScreen()
        chatScreen.clickOnMessageSendBtn()
        val loginScreen = chatScreen.snackLogInAction?.perform(click()) as LoginScreen
    }

    @Test
    fun clickOnSendBtnAndNotSelectChannelAndCheckToastIsDisplayed() {
        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.deleteAllChannels()
        channelScreen.drawer.perform(close())
        chatScreen.clickOnMessageSendBtn()
        chatScreen.checkIsToastDisplayed(mActivityTestRule, chatScreen.toastNoChannelSelected)
    }

    @Test
    fun clickOnSendBtnAndSelectChannelAndCheckToastIsDisplayed() {
        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.addNewChannel()
        channelScreen.clickOnChannel(channelScreen.lastChannel())
        channelScreen.drawer.perform(close())
        chatScreen.clickOnMessageSendBtn()
        chatScreen.checkIsToastDisplayed(mActivityTestRule, chatScreen.toastNoMessageBody)
        channelScreen.drawer.perform(open())
        channelScreen.deleteAllChannels()
    }

    @After
    fun afterEachTest() {
        IdlingRegistry.getInstance().unregister(IdlingResourceHolding.idlingResource)
        UserDataService.logout()
    }
}