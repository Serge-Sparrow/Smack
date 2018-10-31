package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.support.test.espresso.IdlingRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.Services.FindUserByEmailService
import com.example.serhiivorobiov.smack.Services.LoginService
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.TestFramework.Screens.ChatScreen
import com.example.serhiivorobiov.smack.TestFramework.Screens.LoginScreen
import com.example.serhiivorobiov.smack.TestFramework.Utilities.SECOND_VALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.SECOND_VALID_PASSWORD
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_LOGIN
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_PASSWORD
import org.junit.After
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
        IdlingRegistry.getInstance().register(LoginService.loginCountingIdlingResource)
        IdlingRegistry.getInstance().register(FindUserByEmailService.findUserByEmailIR)
    }

    @Test
    fun sendMessageInFirstChannel() {
        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.clickOnChannel(0)
        chatScreen.createNewMessage()
        chatScreen.clickOnMessageSendBtn()
        chatScreen.checkMessageIsDisplayed()

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
    }

    @Test
fun checkSentMessageReceivedByAnotherUser() {
        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.clickOnChannel(channelScreen.lastChannel())
        chatScreen.createNewMessage()
        chatScreen.clickOnMessageSendBtn()
        chatScreen.onBurgerClick()
        channelScreen.onLoginBtnClick()
        channelScreen.onLoginBtnClick()
        loginScreen.clickOnLogInButton(VALID_LOGIN, SECOND_VALID_EMAIL, SECOND_VALID_PASSWORD)
        channelScreen.clickOnChannel(channelScreen.lastChannel())
        chatScreen.checkMessageIsDisplayed()
    }

    @Test
    fun checkIfMessagesInCouldBeScrolled() {
        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.clickOnChannel(0)
        chatScreen.scrollMessages()
    }

    @After
    fun afterEachTest() {
        IdlingRegistry.getInstance().unregister(LoginService.loginCountingIdlingResource)
        IdlingRegistry.getInstance().unregister(FindUserByEmailService.findUserByEmailIR)
    }

    @After
    fun logout() {
        UserDataService.logout()
    }

}