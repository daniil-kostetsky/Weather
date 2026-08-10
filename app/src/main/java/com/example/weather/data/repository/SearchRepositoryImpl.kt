package com.example.weather.data.repository

import com.example.weather.data.mapper.toEntities
import com.example.weather.data.mapper.toEntity
import com.example.weather.data.network.api.ApiService
import com.example.weather.domain.entity.City
import com.example.weather.domain.entity.Forecast
import com.example.weather.domain.entity.Weather
import com.example.weather.domain.repository.SearchRepository
import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {
    override suspend fun search(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }
}