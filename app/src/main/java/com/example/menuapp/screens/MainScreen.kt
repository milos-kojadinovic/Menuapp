package com.example.menuapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.menuapp.R
import com.example.menuapp.data.Venue
import com.example.menuapp.openingTime
import com.example.menuapp.ui.theme.HintColor
import com.example.menuapp.ui.theme.SubtitleColor
import com.example.menuapp.viewmodels.MainViewModel
import com.example.menuapp.viewmodels.MainViewModelIntent
import com.example.menuapp.viewmodels.MainViewModelState
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = getViewModel(), navigateNext: (Venue) -> Unit
) {
    val state = mainViewModel.state.observeAsState(MainViewModelState.Loading).value

    LaunchedEffect(key1 = true) {
        mainViewModel.handleIntent(MainViewModelIntent.LoadData)
    }

    when (state) {
        MainViewModelState.Loading -> {
            LoadingScreen()
        }

        is MainViewModelState.Error -> {
            HandleError(message = state.message)
        }

        is MainViewModelState.LoadedData -> {
            DataLoaded(state.venues, navigateNext)
        }
    }
}

@Composable
fun DataLoaded(venues: List<Venue>, navigateNext: (Venue) -> Unit) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        venues.forEach { venue ->
            venue.ViewView(Modifier.clickable { navigateNext(venue) })
        }
    }
}

@Composable
fun Venue.ViewView(modifier: Modifier) {
    Column(modifier.fillMaxWidth()) {
        Column(Modifier.padding(horizontal = 24.dp)) {
            Text(
                modifier = Modifier.padding(top = 18.dp),
                text = venue.name,
                style = MaterialTheme.typography.bodyMedium,
                color = if (venue.is_open) Color.Black else SubtitleColor
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = distance.toString() + stringResource(R.string.meter),
                style = MaterialTheme.typography.bodySmall,
                color = if (venue.is_open) Color.Black else SubtitleColor
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = venue.address,
                style = MaterialTheme.typography.bodySmall,
                color = SubtitleColor

            )
            val workingTime = venue.openingTime()
            if (venue.is_open && workingTime != null) {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = workingTime,
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = stringResource(R.string.closed),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                color = HintColor.copy(alpha = 0.06f)
            )
        }
    }
}

