package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.graphics.drawable.Drawable
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import android.support.test.espresso.matcher.ViewMatchers.hasBackground
import android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast
import android.support.test.espresso.matcher.ViewMatchers.isEnabled
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.AddUserService
import com.example.serhiivorobiov.smack.Services.AuthService
import com.example.serhiivorobiov.smack.Services.DeleteUserService
import com.example.serhiivorobiov.smack.Services.LoginService
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.TestFramework.Screens.ChannelScreen
import com.example.serhiivorobiov.smack.TestFramework.Screens.ChatScreen
import com.example.serhiivorobiov.smack.TestFramework.Screens.CreateUserScreen
import com.example.serhiivorobiov.smack.TestFramework.Screens.LoginScreen
import com.example.serhiivorobiov.smack.TestFramework.Utilities.CREATE_USER_ERROR_HANDLING
import com.example.serhiivorobiov.smack.TestFramework.Utilities.SUCCESS_NEW_USER_AND_ACC
import com.example.serhiivorobiov.smack.TestFramework.Utilities.SUCCESS_NEW_USER_EXIST_ACC
import com.example.serhiivorobiov.smack.TestFramework.Utilities.USER_NAME
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_EMAIL
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class CreateUserTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun logout() {
        UserDataService.logout()
        IdlingRegistry.getInstance().register(AuthService.registerCountingIR)
        IdlingRegistry.getInstance().register(LoginService.loginCountingIdlingResource)
        IdlingRegistry.getInstance().register(AddUserService.userCountingIdlingResource)
        IdlingRegistry.getInstance().register(DeleteUserService.deleteUserIR)
    }

    @After
    fun deleteCreatedUser() {
        val userName = UserDataService.name
        val userId = UserDataService.id
        if (UserDataService.id != "") {
            DeleteUserService.deleteUser(userName, userId) { _ ->
            }
        }
        IdlingRegistry.getInstance().unregister(DeleteUserService.deleteUserIR)
        IdlingRegistry.getInstance().unregister(AuthService.registerCountingIR)
        IdlingRegistry.getInstance().unregister(LoginService.loginCountingIdlingResource)
        IdlingRegistry.getInstance().unregister(AddUserService.userCountingIdlingResource)
    }

    @Test
    fun createUserUsingExistingAccount() {

            val chatScreen = ChatScreen()
            val channelScreen = chatScreen.onBurgerClick()
            val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
            val createUserScreen = loginScreen.clickOnSignUpButton()
            createUserScreen.onClickCreateUserButton(SUCCESS_NEW_USER_EXIST_ACC)
    }

    @Test
    fun createUserAndAccount() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        val createUserScreen = loginScreen.clickOnSignUpButton()
        createUserScreen.onClickCreateUserButton(SUCCESS_NEW_USER_AND_ACC)
    }

    @Test
    fun checkThatDrawerHeaderContainsCorrectInfoAfterUserCreation() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        val createUserScreen = loginScreen.clickOnSignUpButton()
        createUserScreen.onClickCreateUserButton(SUCCESS_NEW_USER_EXIST_ACC)
        assertThat(channelScreen.userNameTxt,equalTo(USER_NAME))
        assertThat(channelScreen.userEmailTxt,equalTo(VALID_EMAIL))
    }

    @Test
    fun checkIsToastDisplayedIfWrongFieldValidation() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        val createUserScreen = loginScreen.clickOnSignUpButton()
        createUserScreen.onClickCreateUserButton(CREATE_USER_ERROR_HANDLING)
        createUserScreen.checkIsToastDisplayed(mActivityTestRule)
    }
}