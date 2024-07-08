package com.example.jetweatherforcast.network

import com.example.jetweatherforcast.model.Weather
import com.example.jetweatherforcast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "imperial",
        @Query("appid") apiKey: String = Constants.API_KEY
    ): Weather

}