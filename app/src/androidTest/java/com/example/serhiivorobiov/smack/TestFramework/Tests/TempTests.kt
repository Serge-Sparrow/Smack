package com.example.serhiivorobiov.smack.TestFramework.Tests

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.serhiivorobiov.smack.Controller.MainActivity
import com.example.serhiivorobiov.smack.TestFramework.Screens.ChannelScreen
import com.example.serhiivorobiov.smack.TestFramework.Screens.ChatScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class TempTests {

    @get:Rule
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun simpleTest() {

        val chatScreen = ChatScreen()
        val channelScreen =  chatScreen.onBurgerClick() as ChannelScreen
        sleep(100)
        channelScreen.onLoginBtnClick()
    }
}

