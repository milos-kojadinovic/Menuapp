package com.example.menuapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.menuapp.data.ErrorResponse
import com.example.menuapp.data.VenueDetails
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import java.time.LocalDateTime

@Composable
fun VenueDetails.openingTime(now: LocalDateTime = LocalDateTime.now()): String? {
    // Logic of this function needs to be much more complex, but as it is not described in task, making it simple
    val currentDay = now.dayOfWeek.value
    // Current day of week is not the same over the globe. This should be mapped to the same timezone. Ignored in this sample
    val day = serving_times.firstOrNull {
        it.days.contains(currentDay.toDouble())
    }
    return if (day != null) {
        val nowBeforeOpeningTime = now.hour < day.time_from.split(":").first().toInt()
        val nowAfterOpeningTime = now.hour > day.time_to.split(":").first().toInt()
        when {
            !nowBeforeOpeningTime && !nowAfterOpeningTime -> {
                stringResource(id = R.string.working_time, day.time_from, day.time_to)
            }

            nowBeforeOpeningTime -> {
                stringResource(id = R.string.opens_at, day.time_from)
            }

            else -> {
                null
            }
        }
    } else {
        null
    }

}

fun ResponseBody?.getErrorMessage(): String = if (this != null) {
    val gson = GsonBuilder().create()
    val loginErrorResponse =
        gson.fromJson(string(), ErrorResponse::class.java)
    loginErrorResponse.data.message
} else ""