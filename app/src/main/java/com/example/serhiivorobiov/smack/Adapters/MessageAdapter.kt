package com.example.serhiivorobiov.smack.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.serhiivorobiov.smack.Model.Message
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.UserDataService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MessageAdapter(val context: Context, val messages: ArrayList<Message>) : RecyclerView
                    .Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_view, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.bindMessage(context, messages[p1])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.message_user_image)
        val timeStamp = itemView.findViewById<TextView>(R.id.message_date)
        val msgBody = itemView.findViewById<TextView>(R.id.message_body)
        val userName = itemView.findViewById<TextView>(R.id.message_name)

        fun bindMessage(context: Context, message: Message) {

            val resourceId = context.resources.getIdentifier(message.userAvatar, "drawable",
                context.packageName)
            image.setImageResource(resourceId)
            image.setBackgroundColor(UserDataService.returnAvatarColor(message.userAvatarColor))
            userName.text = message.userName
            timeStamp.text = returnDateString(message.timeStamp)
            msgBody.text = message.message
        }
    }
    fun returnDateString(isoString: String): String {
        val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.getDefault())
        isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
        var convertedDate = Date()
        try {
            convertedDate = isoFormatter.parse(isoString)
        } catch (e: ParseException) {
            Log.d("PARSE", "Cannot parse the date")
        }
        val outDateString = SimpleDateFormat("EEE, h:mm a", Locale.getDefault())
        return outDateString.format(convertedDate)
    }
}