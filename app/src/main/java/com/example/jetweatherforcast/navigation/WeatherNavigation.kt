package com.example.jetweatherforcast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetweatherforcast.screen.main.MainScreen
import com.example.jetweatherforcast.screen.main.MainViewModel
import com.example.jetweatherforcast.screen.search.SearchScreen
import com.example.jetweatherforcast.screen.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        composable(WeatherScreens.MainScreen.name) {
            val mainViewmodel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, viewModel = mainViewmodel)
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
    }
}