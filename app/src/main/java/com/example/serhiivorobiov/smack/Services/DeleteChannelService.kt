package com.example.serhiivorobiov.smack.Services

//import android.support.test.espresso.idling.CountingIdlingResource
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.serhiivorobiov.smack.Controller.App
import com.example.serhiivorobiov.smack.Utilities.URL_DELETE_CHANNEL

object DeleteChannelService {

//        val deleteUserIR = CountingIdlingResource("IR for delete user request", true)

        fun deleteChannel(id: String, complete: (Boolean) -> Unit) {
            val url = "$URL_DELETE_CHANNEL$id"

//            deleteUserIR.increment()
            val deleteChannelRequest = object : StringRequest(
                Method.DELETE, url,
                Response.Listener { _ ->
                    try {
                        complete(true)
                    } finally {
//                        deleteUserIR.decrement()
                    }
                },
                Response.ErrorListener { error ->
                    try {
                        Log.d("ERROR", "Could not delete channel: $error")
                        complete(false)
                    } finally {
//                        deleteUserIR.decrement()
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
            App.prefs.requestQueue.add(deleteChannelRequest)
        }
    }