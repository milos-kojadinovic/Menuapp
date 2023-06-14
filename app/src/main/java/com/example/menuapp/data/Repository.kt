package com.example.menuapp.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface Repository {

    @Headers(
        "application: mobile-application",
        "Content-Type: application/json",
        "Device-UUID: 123456",
        "Api-Version: 3.7.0"
    )
    @POST("customers/login")
    suspend fun login(@Body requestBody: LoginRequest): Response<LoginResponse>

    @Headers(
        "application: mobile-application",
        "Content-Type: application/json",
        "Device-UUID: 123456",
        "Api-Version: 3.7.0"
    )
    @POST("directory/search")
    suspend fun loadData(@Body requestBody: LoadListRequest = LoadListRequest()): Response<ItemsInfo>
}