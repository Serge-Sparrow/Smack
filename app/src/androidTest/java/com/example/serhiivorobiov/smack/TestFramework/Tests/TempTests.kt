package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.TestFramework.Screens.ChatScreen
import com.example.serhiivorobiov.smack.TestFramework.Screens.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.serhiivorobiov.smack.TestFramework.Utilities.CREATE_USER_ERROR_HANDLING

@RunWith(AndroidJUnit4::class)
class TempTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun simpleTest() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        val createUserScreen = loginScreen.clickOnSignUpButton()
        createUserScreen.onClickCreateUserButton(CREATE_USER_ERROR_HANDLING)
        createUserScreen.checkIsToastDisplayed(mActivityTestRule)
    }
}

