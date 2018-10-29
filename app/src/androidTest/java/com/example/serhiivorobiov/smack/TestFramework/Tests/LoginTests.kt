package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.Services.FindUserByEmailService
import com.example.serhiivorobiov.smack.Services.LoginService
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.TestFramework.Screens.ChatScreen
import com.example.serhiivorobiov.smack.TestFramework.Screens.LoginScreen
import com.example.serhiivorobiov.smack.TestFramework.Utilities.FAIL_LOGIN
import com.example.serhiivorobiov.smack.TestFramework.Utilities.INVALID_LOGIN
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
class LoginTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIR() {
        IdlingRegistry.getInstance().register(LoginService.loginCountingIdlingResource)
        IdlingRegistry.getInstance().register(FindUserByEmailService.findUserByEmailIR)
    }

    @After
    fun deleteCreatedUser() {
        IdlingRegistry.getInstance().unregister(LoginService.loginCountingIdlingResource)
        IdlingRegistry.getInstance().unregister(FindUserByEmailService.findUserByEmailIR)

    }
    @After
    fun logoutExtra() {
        UserDataService.logout()
    }

    @Test
    fun checkIsToastDisplayedIfTryingToLoginInvalidUser() {
        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(INVALID_LOGIN)
        loginScreen.checkIsToastDisplayed(mActivityTestRule, loginScreen.toastIllegalLogin)
    }

    @Test
    fun checkSighupButton() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        val createUserScreen =loginScreen.clickOnSignUpButton()
    }

    @Test
    fun loginValidUser() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(VALID_LOGIN)
        ViewMatchers.assertThat(channelScreen.userNameTxt, CoreMatchers.equalTo(VALID_USER_NAME))
        ViewMatchers.assertThat(channelScreen.userEmailTxt, CoreMatchers.equalTo(VALID_EMAIL))
    }

    @Test
    fun checkIsToastDisplayedIfWrongFieldValidation() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        loginScreen.clickOnLogInButton(FAIL_LOGIN)
        loginScreen.checkIsToastDisplayed(mActivityTestRule,loginScreen.toastValidation)
    }
}