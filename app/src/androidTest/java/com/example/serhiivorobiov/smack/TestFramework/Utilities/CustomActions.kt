package com.example.serhiivorobiov.smack.TestFramework.Utilities

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.serhiivorobiov.smack.R
import org.hamcrest.Matcher
import java.util.concurrent.atomic.AtomicReference

fun getText(textHolder: AtomicReference<String>): ViewAction {
    class GetTextAction(val textHolder: AtomicReference<String>) : ViewAction {
        override fun getDescription(): String = "Get view text."

        override fun getConstraints(): Matcher<View> =
            ViewMatchers.isAssignableFrom(TextView::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            this.textHolder.set((view as TextView).text.toString())
        }
    }
    return GetTextAction(textHolder)
}

fun clickDeleteChannel(): ViewAction {

    class deleteButton : ViewAction {
        override fun getDescription(): String = "Delete channel button"

        override fun getConstraints(): Matcher<View> =
            ViewMatchers.isAssignableFrom(ImageButton::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            val childView = view?.findViewById<ImageButton>(R.id.delete_channel)
            childView?.performClick()
            }
        }
    return deleteButton()
}

fun clickDeleteMessage(): ViewAction {

    class deleteButton : ViewAction {
        override fun getDescription(): String = "Delete message button"

        override fun getConstraints(): Matcher<View> =
            ViewMatchers.isAssignableFrom(Button::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            val childView = view?.findViewById<Button>(R.id.message_dlt_btn)
            childView?.performClick()
        }
    }
    return deleteButton()
}

fun getTextHint(textHolder: AtomicReference<String>): ViewAction {
    class GetTextAction(val textHolder: AtomicReference<String>) : ViewAction {
        override fun getDescription(): String = "Get view text."

        override fun getConstraints(): Matcher<View> =
            ViewMatchers.isAssignableFrom(TextView::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            this.textHolder.set((view as TextView).hint.toString())
        }
    }
    return GetTextAction(textHolder)
}

fun getChannelName(textHolder: AtomicReference<String>): ViewAction {
    class GetChannelNameAction(val textHolder: AtomicReference<String>) : ViewAction {
        override fun getDescription(): String = "Get channel name."

        override fun getConstraints(): Matcher<View> =
            ViewMatchers.isAssignableFrom(TextView::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            val name = view?.findViewById<TextView>(R.id.channel_name)
            this.textHolder.set((name as TextView).text.toString())
        }
    }
    return GetChannelNameAction(textHolder)
}