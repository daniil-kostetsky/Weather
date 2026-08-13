package com.example.weather.data.repository

import com.example.weather.data.mapper.toEntity
import com.example.weather.data.network.api.ApiService
import com.example.weather.domain.entity.ForecastWeather
import com.example.weather.domain.entity.CurrentWeather
import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {
    override suspend fun getWeather(cityId: Int): CurrentWeather {
        return apiService.loadCurrentWeather("$PREFIX_CITY_ID$cityId").toEntity()
    }

    override suspend fun getForecast(cityId: Int): ForecastWeather {
        return apiService.loadForecast("$PREFIX_CITY_ID$cityId").toEntity()
    }

    private companion object {

        private const val PREFIX_CITY_ID = "id:"
    }
}