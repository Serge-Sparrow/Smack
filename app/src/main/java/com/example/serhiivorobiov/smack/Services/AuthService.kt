package com.example.serhiivorobiov.smack.Services

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.serhiivorobiov.smack.Controller.App
import com.example.serhiivorobiov.smack.Utilities.IdlingResourceHolding
import com.example.serhiivorobiov.smack.Utilities.URL_REGISTER
import org.json.JSONObject

object AuthService {

    fun userRegister(email: String, password: String, complete: (Boolean) -> Unit) {
        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        IdlingResourceHolding.idlingResource.increment()
        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener {
            _ ->
            try {
                complete(true)
            } finally {
                IdlingResourceHolding.idlingResource.decrement()
            }
        }, Response.ErrorListener { error ->
            try {
                Log.d("ERROR", "Could not register user: $error")
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
        App.prefs.requestQueue.add(registerRequest)
    }
}