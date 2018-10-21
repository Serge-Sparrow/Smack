package com.example.serhiivorobiov.smack.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.serhiivorobiov.smack.Controller.App
import com.example.serhiivorobiov.smack.Utilities.URL_ADD_USER
import org.json.JSONException
import org.json.JSONObject

object AddUserService {

fun createUser(context: Context,name: String, email: String, avatarName: String,avatarColor: String , complete:(Boolean)->Unit) {

    val jsonBody = JSONObject()
    jsonBody.put("name",name)
    jsonBody.put("email",email)
    jsonBody.put("avatarName",avatarName)
    jsonBody.put("avatarColor",avatarColor)
    val requestBody = jsonBody.toString()

    val createUserRequest = object : JsonObjectRequest(
        Method.POST, URL_ADD_USER,null,
        Response.Listener { response ->
            try {
                UserDataService.name = response.getString("name")
                UserDataService.email = response.getString("email")
                UserDataService.avatarName = response.getString("avatarName")
                UserDataService.avatarColor = response.getString("avatarColor")
                UserDataService.id = response.getString("_id")
                complete(true)
            }catch (e: JSONException){
                Log.d("JSON","EXC" + e.localizedMessage)
                complete(false)
            }
        },
        Response.ErrorListener { error ->
            Log.d("ERROR","Could not add user: $error")
        }) {
        override fun getBodyContentType(): String {
            return "application/json; charset = utf-8"
        }

        override fun getBody(): ByteArray {
            return requestBody.toByteArray()
        }

        override fun getHeaders(): MutableMap<String, String> {
           val headers = HashMap<String,String>()
            headers.put("Authorization","Bearer ${App.prefs.authToken}")
            return headers
        }

    }
    App.prefs.requestQueue.add(createUserRequest)
}
}
