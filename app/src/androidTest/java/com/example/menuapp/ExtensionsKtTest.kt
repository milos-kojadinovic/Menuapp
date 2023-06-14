package com.example.menuapp

import androidx.compose.ui.test.junit4.createComposeRule
import com.example.menuapp.data.Image
import com.example.menuapp.data.ServingTime
import com.example.menuapp.data.VenueDetails
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class VenueDetailsTest {
//
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testOpenedNow() {
        val venueDetails = venueDetails()
        val currentTime = LocalDateTime.of(2023, 6, 14, 11, 30) // June 14, 2023, 11:30 AM

        composeTestRule.setContent {
            val result = venueDetails.openingTime(currentTime)
            assertEquals("Today 09:00 to 18:00", result)
        }

    }

    @Test
    fun willOpen() {
        val venueDetails = venueDetails()
        val currentTime = LocalDateTime.of(2023, 6, 14, 8, 30) // June 14, 2023, 11:30 AM

        composeTestRule.setContent {
            val result = venueDetails.openingTime(currentTime)
            assertEquals("Opens at 09:00", result)
        }

    }

    @Test
    fun wontOpen() {
        val venueDetails = venueDetails()
        val currentTime = LocalDateTime.of(2023, 6, 14, 20, 30) // June 14, 2023, 11:30 AM

        composeTestRule.setContent {
            val result = venueDetails.openingTime(currentTime)
            assertEquals(null, result)
        }

    }

    private fun venueDetails() = VenueDetails(
        name = "Sample Venue",
        description = "Sample Description",
        welcome_message = null,
        address = "Sample Address",
        serving_times = listOf(
            ServingTime("09:00", "18:00", listOf(1.0, 2.0, 3.0, 4.0, 5.0)),
            ServingTime("10:00", "16:00", listOf(6.0))
        ),
        image = Image("thumbnail_medium_url"),
        is_open = true
    )
}