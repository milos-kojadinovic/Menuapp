package com.example.menuapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ItemsInfo(
    val data: ItemsData
)

data class ItemsData(
    val venues: List<Venue>
)

@Parcelize
data class Venue(
    val distance: Double,
    val distance_in_miles: Double,
    val venue: VenueDetails
) : Parcelable

@Parcelize
data class VenueDetails(
    val name: String,
    val description: String,
    val welcome_message: String?,
    val address: String,
    val serving_times: List<ServingTime>,
    val image: Image,
    val is_open: Boolean,
) : Parcelable

@Parcelize
data class Image(
    val thumbnail_medium: String,
) : Parcelable

@Parcelize
class ServingTime(
    val time_from: String,
    val time_to: String,
    val days: List<Double>
) : Parcelable