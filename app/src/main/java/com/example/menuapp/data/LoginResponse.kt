package com.example.menuapp.data

data class LoginResponse(
    val data: Data
)

data class Data(
    val token: Token,
)

data class Token(
    val value: String,
)