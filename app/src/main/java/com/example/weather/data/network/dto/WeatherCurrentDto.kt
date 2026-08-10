package com.example.weather.data.network.dto

import kotlinx.serialization.SerialName

data class WeatherCurrentDto(
    @SerialName("current") val current: WeatherDto
)
