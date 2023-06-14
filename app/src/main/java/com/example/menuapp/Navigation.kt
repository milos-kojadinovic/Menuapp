package com.example.menuapp

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.menuapp.data.Venue
import com.example.menuapp.screens.LoginScreen
import com.example.menuapp.screens.MainScreen
import com.example.menuapp.screens.VenueDetails

const val VENUE_KEY = "venue"

enum class NavigationRoutes {
    LoginScreen, MainScreen, VenueDetails
}

@Composable
fun NavigationView(token: String?) {
    val navController = rememberNavController()

    val startDestination =
        if (token == null) NavigationRoutes.LoginScreen.name else NavigationRoutes.MainScreen.name

    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable(NavigationRoutes.LoginScreen.name) {
            LoginScreen {
                navController.popBackStack()
                navController.navigate(NavigationRoutes.MainScreen.name)
            }
        }
        composable(NavigationRoutes.MainScreen.name) {
            MainScreen {

                navController.currentBackStackEntry?.savedStateHandle?.set(VENUE_KEY, it)

                navController.navigate(NavigationRoutes.VenueDetails.name)
            }
        }
        composable(
            NavigationRoutes.VenueDetails.name,
        ) {
            val venue = navController
                .previousBackStackEntry?.savedStateHandle?.get<Venue>(VENUE_KEY)

            venue?.let {
                VenueDetails(it) {
                    navController.popBackStack(NavigationRoutes.MainScreen.name, true)
                    navController.navigate(NavigationRoutes.LoginScreen.name)
                }
            }
        }
    }
}

