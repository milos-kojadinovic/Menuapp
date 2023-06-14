package com.example.menuapp.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun HandleError(
    message: String
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}