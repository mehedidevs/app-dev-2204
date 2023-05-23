package com.ju.simplequiz2204.class29

data class Chat(
    val message: String,
    val sender: String,
    val receiver: String,
    val messageId: String,
    val time: String = System.currentTimeMillis().toString()
)