package com.example.serhiivorobiov.smack.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.serhiivorobiov.smack.Model.Channel
import com.example.serhiivorobiov.smack.R

class ChannelAdapter(
    val context: Context, val channels: ArrayList<Channel>, val clickItem: (Channel) -> Unit,
    val clickDelete: (ImageButton) -> Unit
) : RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View, val clickItem: (Channel) -> Unit, val clickDelete: (ImageButton) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val channelName = itemView.findViewById<TextView>(R.id.channel_name)
        val deleteChannel = itemView.findViewById<ImageButton>(R.id.delete_channel)

        fun bindChannel(context: Context, channel: Channel) {
            channelName.text = channel.name
            itemView.setOnClickListener {
                clickItem(channel)
            }
            deleteChannel.setOnClickListener {
                    clickDelete(this.deleteChannel)
                }
            }
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ChannelAdapter.ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.channel_holder, p0, false)
            return ViewHolder(view, clickItem, clickDelete)
        }
    override fun getItemCount(): Int {
        return channels.count()
    }
    override fun onBindViewHolder(p0: ChannelAdapter.ViewHolder, p1: Int) {

        p0.bindChannel(context, channels[p1])

    }
}
