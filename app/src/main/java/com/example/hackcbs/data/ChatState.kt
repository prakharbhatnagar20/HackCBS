package com.example.hackcbs.data

data class ChatState(
    val isEnteringToken: Boolean,
    val remoteToken: String = "",
    val messageText: String = ""
)

data class SendMessageDto(
    val to: String?,
    val notification: NotificationBody
)

data class NotificationBody(
    val title: String,
    val body: String
)
