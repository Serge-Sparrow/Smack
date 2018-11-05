package com.example.serhiivorobiov.smack.Services

import android.support.annotation.VisibleForTesting
import android.util.Log
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonArrayRequest
import com.example.serhiivorobiov.smack.Controller.App
import com.example.serhiivorobiov.smack.Model.Channel
import com.example.serhiivorobiov.smack.Model.Message
import com.example.serhiivorobiov.smack.Utilities.IdlingResourceHolding
import com.example.serhiivorobiov.smack.Utilities.URL_GET_CHANNELS
import com.example.serhiivorobiov.smack.Utilities.URL_GET_MESSAGES
import org.json.JSONException

object MessageService {

    @VisibleForTesting
    val channels = ArrayList<Channel>()
    @VisibleForTesting
    val messages = ArrayList<Message>()

    fun getChannels(complete: (Boolean) -> Unit) {
        IdlingResourceHolding.idlingResource.increment()
        val channelsRequest = object : JsonArrayRequest(Method.GET, URL_GET_CHANNELS, null,
            Listener { response ->
            clearChannels()
        try {
            for (x in 0 until response.length()) {
                val channel = response.getJSONObject(x)
                val chanName = channel.getString("name")
                val chanDesc = channel.getString("description")
                val chanId = channel.getString("_id")

                val newChan = Channel(chanName, chanDesc, chanId)
                this.channels.add(newChan)
            }
            complete(true)
        } catch (e: JSONException) {
                Log.d("JSON", "EXC: " + e.localizedMessage)
                complete(false)
            } finally {
            IdlingResourceHolding.idlingResource.decrement()
            }
        },
            Response.ErrorListener { _ ->
                try {
                    Log.d("ERROR", "Could not retrieve channels")
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
        App.prefs.requestQueue.add(channelsRequest)
    }

    fun getMessages(channelId: String, complete: (Boolean) -> Unit) {
        val url = "$URL_GET_MESSAGES$channelId"
        IdlingResourceHolding.idlingResource.increment()
        val messagesRequest = object : JsonArrayRequest(Method.GET, url, null,
            Listener { response ->
            clearMessages()
            try {
                for (x in 0 until response.length()) {

                    val message = response.getJSONObject(x)
                    val msgBody = message.getString("messageBody")
                    val msgChannelId = message.getString("channelId")
                    val msgUserName = message.getString("userName")
                    val msgUserAvatar = message.getString("userAvatar")
                    val msgUserAvatarColor = message.getString("userAvatarColor")
                    val msgId = message.getString("_id")
                    val msgTime = message.getString("timeStamp")

                    val newMessage = Message(msgBody, msgUserName, msgChannelId, msgUserAvatar,
                        msgUserAvatarColor, msgId, msgTime)
                    this.messages.add(newMessage)
                }
                complete(true)
            } catch (e: JSONException) {
                Log.d("JSON", "EXC: " + e.localizedMessage)
                complete(false)
            } finally {
                IdlingResourceHolding.idlingResource.decrement()
            }
        },
            Response.ErrorListener { _ ->
                try {
                    Log.d("ERROR", "Could not retrieve messages")
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
        App.prefs.requestQueue.add(messagesRequest)
    }

    fun clearMessages() {
        messages.clear()
    }

    fun clearChannels() {
        channels.clear()
    }
}