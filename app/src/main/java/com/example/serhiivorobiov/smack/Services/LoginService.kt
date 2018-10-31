package com.example.serhiivorobiov.smack.Services

import android.support.annotation.VisibleForTesting
import android.support.test.espresso.idling.CountingIdlingResource
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.serhiivorobiov.smack.Controller.App
import com.example.serhiivorobiov.smack.Utilities.URL_LOGIN
import org.json.JSONException
import org.json.JSONObject

object LoginService {
    val loginCountingIdlingResource = CountingIdlingResource("Idling for Login request", true)

    fun userLogin(email: String, password: String, complete: (Boolean) -> Unit) {

        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()
        loginCountingIdlingResource.run {
            dumpStateToLogs()
            increment()
            dumpStateToLogs()
        }
val loginRequest = object : JsonObjectRequest(Method.POST, URL_LOGIN, null,
    Response.Listener { response ->
        try {
            App.prefs.userEmail = response.getString("user")
            App.prefs.authToken = response.getString("token")
            App.prefs.isLoggedIn = true
            complete(true)
        } catch (e: JSONException) {
            Log.d("JSON", "EXC" + e.localizedMessage)
            complete(false)
        } finally {
            loginCountingIdlingResource.dumpStateToLogs()
            loginCountingIdlingResource.decrement()
        }
    },
    Response.ErrorListener { error ->
        Log.d("ERROR", "Could not login user: $error")
        try {
            complete(false)
        } finally {
            loginCountingIdlingResource.decrement()
        }
    }) {
    override fun getBodyContentType(): String {
        return "application/json; charset = utf-8"
    }

    override fun getBody(): ByteArray {
        return requestBody.toByteArray()
    }
}
        App.prefs.requestQueue.add(loginRequest)
    }
}