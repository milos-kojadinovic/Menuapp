package com.example.menuapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.menuapp.test.TestTag

@Composable
 fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize().testTag(TestTag.LOADING_SPINNER),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(Modifier.size(120.dp))
    }
}