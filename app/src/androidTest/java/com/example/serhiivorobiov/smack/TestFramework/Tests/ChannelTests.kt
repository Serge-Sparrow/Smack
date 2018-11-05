package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.doubleClick
import android.support.test.espresso.contrib.DrawerActions.close
import android.support.test.espresso.contrib.DrawerActions.open
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.TestFramework.Screens.ChatScreen
import com.example.serhiivorobiov.smack.TestFramework.Screens.LoginScreen
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_LOGIN
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_PASSWORD
import com.example.serhiivorobiov.smack.Utilities.IdlingResourceHolding
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChannelTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    companion object {

        @AfterClass
        @JvmStatic fun teardown() {
        }
    }

    @Before
    fun beforeEachTest() {
        IdlingRegistry.getInstance().register(IdlingResourceHolding.idlingResource)
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
        channelScreen.drawer.perform(open())
        channelScreen.deleteAllChannels()
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
        channelScreen.drawer.perform(open())
        channelScreen.deleteAllChannels()
    }

    @Test
    fun checkIfCreatedChannelDeleted() {
        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.addNewChannel()
        channelScreen.scrollToChannel(channelScreen.lastChannel())
        channelScreen.checkIsChannelDisplayed(channelScreen.channelNameHolder)
        channelScreen.deleteChannelAtPosition(channelScreen.lastChannel())
        channelScreen.checkIsChannelDeleted(channelScreen.channelNameHolder)
        channelScreen.deleteAllChannels()
    }

    @Test
    fun checkIfFirstChannelSelectedAfterLogin() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        channelScreen.addNewChannel()
        channelScreen.loginBtn.perform(doubleClick())
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        val nameHeader = channelScreen.channelName(0)
        channelScreen.drawer.perform(close())
        chatScreen.checkChannelNameHeader(nameHeader)
        channelScreen.drawer.perform(open())
        channelScreen.deleteAllChannels()
    }

    @Test
    fun checkIfChannelListScrollable() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN, VALID_EMAIL, VALID_PASSWORD)
        for (channelsToAdd in 0..10) channelScreen.addNewChannel()
        channelScreen.scrollToChannel(channelScreen.lastChannel())
        channelScreen.checkIsChannelDisplayed(channelScreen.channelNameHolder)
        channelScreen.deleteAllChannels()
    }

    @After
    fun afterEachTest() {
        IdlingRegistry.getInstance().unregister(IdlingResourceHolding.idlingResource)
            UserDataService.logout()
    }
}