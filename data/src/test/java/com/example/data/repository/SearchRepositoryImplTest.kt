package com.example.data.repository

import com.example.data.network.api.ApiService
import com.example.data.network.dto.CityDto
import com.example.data.network.dto.WeatherCurrentDto
import com.example.data.network.dto.WeatherForecastDto
import com.example.domain.entity.City
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchRepositoryImplTest {

    @Test
    fun `search passes query to api and maps city dto to domain city`() = runBlocking {
        val api = FakeApiService(
            cities = listOf(CityDto(id = 1, name = "Moscow", country = "Russia"))
        )
        val repository = SearchRepositoryImpl(api)

        val result = repository.search("Moscow")

        assertEquals("Moscow", api.receivedSearchQuery)
        assertEquals(
            listOf(City(id = 1, name = "Moscow", country = "Russia")),
            result
        )
    }

    private class FakeApiService(
        private val cities: List<CityDto>
    ) : ApiService {
        var receivedSearchQuery: String? = null

        override suspend fun searchCity(query: String): List<CityDto> {
            receivedSearchQuery = query
            return cities
        }

        override suspend fun loadCurrentWeather(query: String): WeatherCurrentDto =
            error("Current weather is not used in this test")

        override suspend fun loadForecast(query: String, daysCount: Int): WeatherForecastDto =
            error("Forecast is not used in this test")
    }
}
