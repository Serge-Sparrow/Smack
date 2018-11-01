package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.Services.AddUserService
import com.example.serhiivorobiov.smack.Services.AuthService
import com.example.serhiivorobiov.smack.Services.DeleteUserService
import com.example.serhiivorobiov.smack.Services.LoginService
import com.example.serhiivorobiov.smack.Services.UserDataService
import com.example.serhiivorobiov.smack.TestFramework.Screens.ChatScreen
import com.example.serhiivorobiov.smack.TestFramework.Screens.LoginScreen
import com.example.serhiivorobiov.smack.TestFramework.Utilities.CREATE_USER_ERROR_HANDLING
import com.example.serhiivorobiov.smack.TestFramework.Utilities.EMAIL_HINT
import com.example.serhiivorobiov.smack.TestFramework.Utilities.PASSWORD_HINT
import com.example.serhiivorobiov.smack.TestFramework.Utilities.SUCCESS_NEW_USER_AND_ACC
import com.example.serhiivorobiov.smack.TestFramework.Utilities.SUCCESS_NEW_USER_EXIST_ACC
import com.example.serhiivorobiov.smack.TestFramework.Utilities.USER_NAME_HINT
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_EMAIL
import com.example.serhiivorobiov.smack.TestFramework.Utilities.VALID_USER_NAME
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateUserTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIR() {
        IdlingRegistry.getInstance().register(AuthService.registerCountingIR)
        IdlingRegistry.getInstance().register(LoginService.loginCountingIdlingResource)
        IdlingRegistry.getInstance().register(AddUserService.userCountingIdlingResource)
        IdlingRegistry.getInstance().register(DeleteUserService.deleteUserIR)
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
        assertThat(channelScreen.userNameTxt, equalTo(VALID_USER_NAME))
        assertThat(channelScreen.userEmailTxt, equalTo(VALID_EMAIL))
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

    @Test
    fun checkIsPasswordTextHintIsCorrect() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        val createUserScreen = loginScreen.clickOnSignUpButton()
        assertThat(createUserScreen.passwordHint, equalTo(PASSWORD_HINT))
    }

    @Test
    fun checkIsEmailTextHintIsCorrect() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        val createUserScreen = loginScreen.clickOnSignUpButton()
        assertThat(createUserScreen.emailHint, equalTo(EMAIL_HINT))
    }

    @Test
    fun checkIsUserNameTextHintIsCorrect() {

        val chatScreen = ChatScreen()
        val channelScreen = chatScreen.onBurgerClick()
        val loginScreen = channelScreen.onLoginBtnClick() as LoginScreen
        val createUserScreen = loginScreen.clickOnSignUpButton()
        assertThat(createUserScreen.userNameHint, equalTo(USER_NAME_HINT))
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
        UserDataService.logout()
    }
}