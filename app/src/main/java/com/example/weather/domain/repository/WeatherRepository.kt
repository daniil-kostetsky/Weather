package com.example.weather.domain.repository

import com.example.weather.domain.entity.City
import com.example.weather.domain.entity.Forecast
import com.example.weather.domain.entity.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {



    suspend fun getWeather(cityId: Int): Weather
    suspend fun getForecast(cityId: Int): Forecast

}