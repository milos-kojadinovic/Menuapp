package com.example.menuapp

import android.content.Context

const val SHARED_PREFS = "menu_shared_prefs"
const val TOKEN = "token"

fun Context.saveToken(token: String) {
    val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(TOKEN, token)
    editor.apply()
}

fun Context.getToken(): String? {
    val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    return sharedPreferences.getString(TOKEN, null)
}

fun Context.clearToken() {
    val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
}
