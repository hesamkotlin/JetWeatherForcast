package com.example.jetweatherforcast.screen.about

import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.jetweatherforcast.R
import com.example.jetweatherforcast.widgets.WeatherAppBar

@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "About",
            isMainScreen = false,
            icon = Icons.Default.ArrowBack,
            navController = navController
        ) { navController.popBackStack() }
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(id = R.string.about_app),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}