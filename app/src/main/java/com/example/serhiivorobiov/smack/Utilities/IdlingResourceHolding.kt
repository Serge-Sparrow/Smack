package com.example.serhiivorobiov.smack.Utilities

import android.support.test.espresso.idling.CountingIdlingResource

object IdlingResourceHolding {

    val idlingResource = CountingIdlingResource("Network IR", true)
}