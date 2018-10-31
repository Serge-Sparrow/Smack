package com.example.serhiivorobiov.smack.Services

import android.support.annotation.VisibleForTesting
import android.support.test.espresso.idling.CountingIdlingResource
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.serhiivorobiov.smack.Controller.App
import com.example.serhiivorobiov.smack.Utilities.URL_DELETE_USER
import org.json.JSONObject

@VisibleForTesting
object DeleteUserService {
val deleteUserIR = CountingIdlingResource("IR for delete user request", true)
    fun deleteUser(name: String, id: String, complete: (Boolean) -> Unit) {
        val url = "$URL_DELETE_USER$id"

        val jsonBody = JSONObject()
        jsonBody.put("name", name)
        val requestBody = jsonBody.toString()
        deleteUserIR.increment()
        val deleteUserRequest = object : StringRequest(
            Method.DELETE, url,
            Response.Listener { _ ->
                try {
                    complete(true)
                } finally {
                    deleteUserIR.decrement()
                }
            },
            Response.ErrorListener { error -> Log.d("ERROR", "Could not delete user: $error")
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset = utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${App.prefs.authToken}"
                return headers
            }
        }
        App.prefs.requestQueue.add(deleteUserRequest)
    }
}