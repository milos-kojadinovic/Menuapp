package com.example.menuapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.menuapp.R
import com.example.menuapp.clearToken
import com.example.menuapp.data.Venue
import com.example.menuapp.openingTime
import com.example.menuapp.test.TestTag
import com.example.menuapp.ui.theme.SubtitleColor

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VenueDetails(venue: Venue, logout: () -> Unit) {
    val context = LocalContext.current
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopEnd
        ) {
            GlideImage(
                modifier = Modifier
                    .testTag(TestTag.IMAGE)
                    .fillMaxSize(),
                model = venue.venue.image.thumbnail_medium,
                contentDescription = venue.venue.name,
                contentScale = ContentScale.FillHeight
            )
            Button(
                modifier = Modifier
                    .testTag(TestTag.LOGOUT)
                    .padding(end = 24.dp, top = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = {
                    context.clearToken()
                    logout()
                }) {
                Text(
                    text = stringResource(R.string.logout),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 20.dp)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = venue.venue.name.replace('+', ' '),
                style = MaterialTheme.typography.headlineSmall,
            )

            if (!venue.venue.welcome_message.isNullOrBlank()) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = venue.venue.welcome_message,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = venue.venue.description,
                style = MaterialTheme.typography.bodySmall,
                color = SubtitleColor
            )

            val openingTime = venue.venue.openingTime()
            if (openingTime != null) Text(
                modifier = Modifier.padding(top = 8.dp),
                text = openingTime,
                style = MaterialTheme.typography.bodySmall
            ) else {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = stringResource(R.string.closed),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

}