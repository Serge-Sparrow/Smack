package com.example.serhiivorobiov.smack.Model

import com.android.volley.toolbox.StringRequest

class Message constructor(val message: String, val userName: String, val channelId: String,
                          val userAvatar:String, val userAvataracolor: String, val id: String, val timeStamp: String)