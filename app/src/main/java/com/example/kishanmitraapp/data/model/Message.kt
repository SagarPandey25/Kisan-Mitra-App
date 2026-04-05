package com.example.kishanmitraapp.data.model

data class Message(
    val text: String,
    val isUser: Boolean,
    val isError: Boolean = false
)
