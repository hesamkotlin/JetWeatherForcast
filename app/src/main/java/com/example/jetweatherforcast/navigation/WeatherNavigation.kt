package com.example.jetweatherforcast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetweatherforcast.screen.favorites.FavoritesScreen
import com.example.jetweatherforcast.screen.about.AboutScreen
import com.example.jetweatherforcast.screen.main.MainScreen
import com.example.jetweatherforcast.screen.main.MainViewModel
import com.example.jetweatherforcast.screen.search.SearchScreen
import com.example.jetweatherforcast.screen.setting.SettingsScreen
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
        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}", arguments = listOf(navArgument(name = "city") {
            type = NavType.StringType
        })) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewmodel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, viewModel = mainViewmodel, city = city)
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }

        composable(WeatherScreens.FavoriteScreen.name) {
            FavoritesScreen(navController = navController)
        }
    }
}