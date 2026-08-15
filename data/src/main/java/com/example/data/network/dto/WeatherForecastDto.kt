package com.example.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastDto(
    @SerialName("current") val current: WeatherDto,
    @SerialName("forecast") val forecastDto: ForecastDto
)