package com.example.menuapp.data

data class ErrorResponse(
    val status: String,
    val code: Int,
    val data: ErrorData
)

data class ErrorData(
    val message: String,
)
