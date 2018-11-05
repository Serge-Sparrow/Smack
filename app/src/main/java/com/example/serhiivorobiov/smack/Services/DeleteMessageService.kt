package com.example.serhiivorobiov.smack.Services

import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.serhiivorobiov.smack.Controller.App
import com.example.serhiivorobiov.smack.Utilities.IdlingResourceHolding
import com.example.serhiivorobiov.smack.Utilities.URL_DELETE_MESSAGE

object DeleteMessageService {

    fun deleteMessage(id: String, complete: (Boolean) -> Unit) {
        val url = "$URL_DELETE_MESSAGE$id"

        IdlingResourceHolding.idlingResource.increment()
        val deleteMessageRequest = object : StringRequest(
            Method.DELETE, url,
            Response.Listener { _ ->
                try {
                    complete(true)
                } finally {
                    IdlingResourceHolding.idlingResource.decrement()
                }
            },
            Response.ErrorListener { error ->
                try {
                    Log.d("ERROR", "Could not delete message: $error")
                    complete(false)
                } finally {
                    IdlingResourceHolding.idlingResource.decrement()
                }
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset = utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${App.prefs.authToken}"
                return headers
            }
        }
        App.prefs.requestQueue.add(deleteMessageRequest)
    }
}