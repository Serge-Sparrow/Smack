package com.example.serhiivorobiov.smack.Services

import android.content.Context
import android.content.Intent
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.serhiivorobiov.smack.Controller.App
import com.example.serhiivorobiov.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import com.example.serhiivorobiov.smack.Utilities.URL_FIND_USER
import org.json.JSONException

object FindUserByEmailService {
    val findUserByEmailIR = CountingIdlingResource("IR for find user by email request", true)
    fun findUser(context: Context, complete: (Boolean) -> Unit) {

        findUserByEmailIR.increment()
        val findUserRequest = object : JsonObjectRequest(Method.GET, "$URL_FIND_USER${App.prefs.userEmail}",
            null,
            Response.Listener { response ->
                try {
                    UserDataService.name = response.getString("name")
                    UserDataService.email = response.getString("email")
                    UserDataService.avatarName = response.getString("avatarName")
                    UserDataService.avatarColor = response.getString("avatarColor")
                    UserDataService.id = response.getString("_id")

                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                    LocalBroadcastManager.getInstance(context).sendBroadcast(userDataChange)
                    complete(true)
                } catch (e: JSONException) {
                    Log.d("JSON", "EXC: " + e.localizedMessage)
                    complete(false)
                }finally {
                    findUserByEmailIR.decrement()
                }
            },
            Response.ErrorListener { _ ->
              try {
                  Log.d("ERROR", "Could not find the user.")
                  complete(false)
              } finally {
                  findUserByEmailIR.decrement()
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
        App.prefs.requestQueue.add(findUserRequest)
    }
}