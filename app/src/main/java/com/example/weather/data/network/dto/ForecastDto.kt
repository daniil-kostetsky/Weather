package com.example.weather.data.network.dto

import kotlinx.serialization.SerialName

data class ForecastDto(
    @SerialName("forecastday")
    val forecastDay: List<DayDto>
)