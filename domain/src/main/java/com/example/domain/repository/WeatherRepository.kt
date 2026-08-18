package com.example.domain.repository

import com.example.domain.entity.ForecastWeather
import com.example.domain.entity.CurrentWeather

interface WeatherRepository {
    suspend fun getWeather(cityId: Int): CurrentWeather
    suspend fun getForecast(cityId: Int): ForecastWeather
}