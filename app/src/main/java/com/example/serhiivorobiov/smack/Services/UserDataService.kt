package com.example.serhiivorobiov.smack.Services

import android.graphics.Color
import android.support.annotation.VisibleForTesting
import com.example.serhiivorobiov.smack.Controller.App
import java.util.Scanner

object UserDataService {

    @VisibleForTesting
    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var name = ""
    @VisibleForTesting
    var email = ""
    var avatarResId = 0
    @VisibleForTesting
    fun logout() {
        avatarResId = 0
        id = ""
        avatarColor = ""
        avatarName = ""
        name = ""
        email = ""
        App.prefs.authToken = ""
        App.prefs.userEmail = ""
        App.prefs.isLoggedIn = false
        MessageService.clearMessages()
        MessageService.clearChannels()
    }

    fun returnAvatarColor(components: String): Int {

        val strippedColor = components
            .replace("[", "")
            .replace("]", "")
            .replace(",", "")

        var r = 0
        var g = 0
        var b = 0

        val scanner = Scanner(strippedColor)
        if (scanner.hasNext()) {
            r = (scanner.nextDouble() * 255).toInt()
            g = (scanner.nextDouble() * 255).toInt()
            b = (scanner.nextDouble() * 255).toInt()
        }
        return Color.rgb(r, g, b)
    }
}