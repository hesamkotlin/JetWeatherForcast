package com.example.jetweatherforcast.di

import android.content.Context
import androidx.room.Room
import com.example.jetweatherforcast.data.WeatherDao
import com.example.jetweatherforcast.data.WeatherDataBase
import com.example.jetweatherforcast.network.WeatherApi
import com.example.jetweatherforcast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDataBase.WeatherDatabase): WeatherDao
            = weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDataBase.WeatherDatabase
            = Room.databaseBuilder(
        context,
        WeatherDataBase.WeatherDatabase::class.java,
        "weather_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

}