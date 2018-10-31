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
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_LOGIN
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_PASSWORD
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChannelTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun beforeEachTest() {
        IdlingRegistry.getInstance().register(LoginService.loginCountingIdlingResource)
        IdlingRegistry.getInstance().register(FindUserByEmailService.findUserByEmailIR)
    }

    @Test
    fun checkIsToastDisplayedIfNotLoginAndClickNewChannelBtn() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        channelScreen.clickOnAddChannelBtn()
        channelScreen.checkIsToastDisplayed(mActivityTestRule)
    }

    @Test
    fun addNewChannelTest() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.addNewChannel()
        channelScreen.clickOnChannel(channelScreen.lastChannel())
        chatScreen.checkChannelNameHeader(channelScreen.channelNameHolder)
    }

    @Test
    fun checkIfChannelsWereLoadedAfterLogout() {
        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.addNewChannel()
        channelScreen.clickOnChannel(channelScreen.lastChannel())
        chatScreen.checkChannelNameHeader(channelScreen.channelNameHolder)
        chatScreen.onBurgerClick()
        channelScreen.onLoginBtnClick()
        channelScreen.onLoginBtnClick()
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.clickOnChannel(channelScreen.lastChannel())
        chatScreen.checkChannelNameHeader(channelScreen.channelNameHolder)
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
