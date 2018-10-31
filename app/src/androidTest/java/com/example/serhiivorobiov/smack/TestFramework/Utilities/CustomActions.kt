package com.example.serhiivorobiov.smack.TestFramework.Utilities

import android.graphics.Color.BLACK
import android.graphics.Color.GREEN
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers
import android.view.View
import android.widget.TextView
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

fun replaceTextChildViewWithId(id: Int, message: String): ViewAction {

    class ReplaceTextInChildView : ViewAction {
        override fun getDescription(): String = "Replace message text"

        override fun getConstraints(): Matcher<View> =
            ViewMatchers.isAssignableFrom(TextView::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            val childView = view?.findViewById<TextView>(id)
            childView?.textSize = 25f
            childView?.text = message
            childView?.highlightColor = BLACK
            childView?.setTextColor(GREEN)
            }
        }
    return ReplaceTextInChildView()
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
