package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.Services.LoginService
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.TestFramework.Screens.ChatScreen
import com.example.serhiivorobiov.smack.TestFramework.Screens.LoginScreen
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_LOGIN
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_USER_NAME
import org.hamcrest.CoreMatchers
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
        UserDataService.logout()
        IdlingRegistry.getInstance().register(LoginService.loginCountingIdlingResource)
    }

    @After
    fun afterEachTest() {
        IdlingRegistry.getInstance().unregister(LoginService.loginCountingIdlingResource)
    }

    @Test
    fun checkIsToastDisplayedIfNotLoginAndClickNewChannelBtn() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        channelScreen.clickOnAddChannelBtn()
        channelScreen.checkIsToastDisplayed(mActivityTestRule)
    }

    @Test
    fun checkIfExistingChannelsAreDisplayedAfterSuccessLogin() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN)
        ViewMatchers.assertThat(channelScreen.userNameTxt, CoreMatchers.equalTo(VALID_USER_NAME))
        ViewMatchers.assertThat(channelScreen.userEmailTxt, CoreMatchers.equalTo(VALID_EMAIL))

    }

}
