package com.example.jetweatherforcast.screen.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.jetweatherforcast.R
import com.example.jetweatherforcast.data.DataOrException
import com.example.jetweatherforcast.model.Weather
import com.example.jetweatherforcast.model.WeatherItem
import com.example.jetweatherforcast.navigation.WeatherScreens
import com.example.jetweatherforcast.utils.formatDate
import com.example.jetweatherforcast.utils.formatDateTime
import com.example.jetweatherforcast.utils.formatDecimals
import com.example.jetweatherforcast.widgets.HumidityWindPressureRow
import com.example.jetweatherforcast.widgets.SunsetSunriseRow
import com.example.jetweatherforcast.widgets.WeatherAppBar
import com.example.jetweatherforcast.widgets.WeatherDetailRow
import com.example.jetweatherforcast.widgets.WeatherStateImage

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
        MainScaffold(weatherData.data!!, navController)
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city.name + " ${weather.city.country}",
                navController = navController,
                elevation = 5.dp, onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            ) {
                Log.d("CLICKED", " CLICKED ")
            }
        }
    ) { content ->
        MainContent(
            data = weather,
            navController = navController,
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Composable
fun MainContent(data: Weather, navController: NavController, modifier: Modifier = Modifier) {
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
                WeatherStateImage(imageURL = imageUrl)
                Text(
                    text = formatDecimals(data.list[0].temp.day) + "º",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp
                )
                Text(
                    text = data.list[0].weather[0].main,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 15.sp
                )
            }
        }
        HumidityWindPressureRow(weather = data.list[0])
        HorizontalDivider()
        SunsetSunriseRow(weather = data.list[0])
        Text(
            text = "This week",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displayMedium
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)) {
                items(items = data.list) { item: WeatherItem ->
                    WeatherDetailRow(item)
                }
            }
        }
    }

}
