package com.example.serhiivorobiov.smack.Services

import android.support.annotation.VisibleForTesting
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.serhiivorobiov.smack.Controller.App
import com.example.serhiivorobiov.smack.Utilities.IdlingResourceHolding
import com.example.serhiivorobiov.smack.Utilities.URL_LOGIN
import org.json.JSONException
import org.json.JSONObject

object LoginService {
@VisibleForTesting
    fun userLogin(email: String, password: String, complete: (Boolean) -> Unit) {

        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        IdlingResourceHolding.idlingResource.increment()
        val loginRequest = object : JsonObjectRequest(Method.POST, URL_LOGIN, null,
            Response.Listener { response ->
                try {
                    App.prefs.userEmail = response.getString("user")
                    App.prefs.authToken = response.getString("token")
                    DeleteAllMessagesService.token = App.prefs.authToken as String
                    App.prefs.isLoggedIn = true
                    complete(true)
        } catch (e: JSONException) {
            Log.d("JSON", "EXC" + e.localizedMessage)
            complete(false)
        } finally {
            IdlingResourceHolding.idlingResource.decrement()
        }
    },
    Response.ErrorListener { error ->
        try {
            Log.d("ERROR", "Could not login user: $error")
            complete(false)
        } finally {
            IdlingResourceHolding.idlingResource.decrement()
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