package com.example.menuapp.module

import com.example.menuapp.data.Repository
import com.example.menuapp.viewmodels.LoginViewModel
import com.example.menuapp.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val menuModule = module {
    single { getRepository() }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
}

fun getRepository(): Repository = run {
    Retrofit.Builder()
        .baseUrl("https://api-qa.menu.app/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(Repository::class.java)
}