package com.example.jetweatherforcast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetweatherforcast.model.Favorite
import com.example.jetweatherforcast.model.Unit

abstract class WeatherDataBase {
    @Database(entities = [Favorite::class, Unit::class], version = 2 , exportSchema = false)
    abstract class WeatherDatabase : RoomDatabase() {
        abstract fun weatherDao(): WeatherDao
    }
}