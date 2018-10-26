package com.example.serhiivorobiov.smack.Utilities

const val BASE_URL = "https://sparrow-chat.herokuapp.com/v1/"
const val SOCKET_URL = "https://sparrow-chat.herokuapp.com/"
const val URL_REGISTER = "${BASE_URL}account/register"
const val URL_LOGIN = "${BASE_URL}account/login"
const val URL_ADD_USER = "${BASE_URL}user/add"
const val URL_FIND_USER = "${BASE_URL}user/byEmail/"
const val URL_GET_CHANNELS = "${BASE_URL}channel"
const val URL_GET_MESSAGES = "${BASE_URL}message/byChannel/"
const val URL_DELETE_USER = "${BASE_URL}user/"

const val BROADCAST_USER_DATA_CHANGE = "BROADCAST USER DATA CHANGE"