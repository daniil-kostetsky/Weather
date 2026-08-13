package com.example.weather.domain.repository

import com.example.weather.domain.entity.ForecastWeather
import com.example.weather.domain.entity.CurrentWeather

interface WeatherRepository {



    suspend fun getWeather(cityId: Int): CurrentWeather
    suspend fun getForecast(cityId: Int): ForecastWeather

}