package com.example.menuapp

import android.app.Application
import com.example.menuapp.module.menuModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MenuApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MenuApplication)
            modules(menuModule)
        }
    }
}