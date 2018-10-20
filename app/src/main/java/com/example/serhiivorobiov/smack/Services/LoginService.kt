package com.example.serhiivorobiov.smack.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.serhiivorobiov.smack.Utilities.URL_LOGIN
import org.json.JSONException
import org.json.JSONObject

object LoginService {

    fun userLogin(context: Context, email: String, password: String, complete:(Boolean)->Unit){

        val jsonBody = JSONObject()
        jsonBody.put("email",email)
        jsonBody.put("password",password)
        val requestBody = jsonBody.toString()

val loginRequest = object : JsonObjectRequest(Method.POST, URL_LOGIN,null,
    Response.Listener {response ->
        try {
            AuthService.userEmail = response.getString("user")
            AuthService.authToken = response.getString("token")
            AuthService.isLoggedIn = true
            complete(true)
        }catch (e: JSONException){
            Log.d("JSON","EXC" + e.localizedMessage)
            complete(false)
        }
    },
    Response.ErrorListener { error ->
        Log.d("ERROR","Could not login user: $error")
    }){
    override fun getBodyContentType(): String {
        return "application/json; charset = utf-8"
    }

    override fun getBody(): ByteArray {
        return requestBody.toByteArray()
    }
}
Volley.newRequestQueue(context).add(loginRequest)
    }
}