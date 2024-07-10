package com.example.jetweatherforcast.screen.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.jetweatherforcast.data.DataOrException
import com.example.jetweatherforcast.model.Weather
import com.example.jetweatherforcast.utils.formatDate
import com.example.jetweatherforcast.utils.formatDecimals
import com.example.jetweatherforcast.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    mainScreen(viewModel, navController)
}

@Composable
fun mainScreen(viewModel: MainViewModel, navController: NavController) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = viewModel.getWeatherData(city = "istanbul")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        mainScaffold(weatherData.data!!, navController)
    }
}

@Composable
fun mainScaffold(weather: Weather, navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city.name + " ${weather.city.country}",
                navController = navController,
                elevation = 5.dp
            ) {
                Log.d("CLICKED", " CLICKED ")
            }
        }
    ) {
        mainContent(
            data = weather,
            navController = navController,
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Composable
fun mainContent(data: Weather, navController: NavController, modifier: Modifier = Modifier) {
    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"

    Column(
        modifier = modifier
            .padding(4.dp, top = 100.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = (formatDate(data.list[0].dt)), // wed, jul 10
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                weatherStateImage(imageURL = imageUrl)
                Text(
                    text = formatDecimals(data.list[0].temp.day),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp
                )
                Text(
                    text = data.list[0].weather[0].main + "º",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 15.sp
                )
            }
        }

    }
}

@Composable
fun weatherStateImage(imageURL: String) {
    Image(
        painter = rememberAsyncImagePainter(imageURL),
        contentDescription = " icon image",
        modifier = Modifier.size(80.dp)
    )
}
