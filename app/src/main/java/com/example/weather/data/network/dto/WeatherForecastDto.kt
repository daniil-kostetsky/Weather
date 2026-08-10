package com.example.weather.data.network.dto

import kotlinx.serialization.SerialName

data class WeatherForecastDto(
    @SerialName("current") val current: WeatherDto,
    @SerialName("forecast") val forecastDto: ForecastDto
)