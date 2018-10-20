package com.example.serhiivorobiov.smack.Services

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.serhiivorobiov.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import com.example.serhiivorobiov.smack.Utilities.URL_FIND_USER
import org.json.JSONException

object findUserByEmailService {

    fun findUser(context: Context, complete: (Boolean) -> Unit) {

        val findUserRequest = object : JsonObjectRequest(Method.GET, "$URL_FIND_USER${AuthService.userEmail}",
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
                }
            },
            Response.ErrorListener { error ->
                Log.d("ERROR", "Could not find the user.")
                complete(false)
            }) {

            override fun getBodyContentType(): String {
                return "application/json; charset = utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${AuthService.authToken}"
                return headers
            }

        }
        Volley.newRequestQueue(context).add(findUserRequest)
    }
}