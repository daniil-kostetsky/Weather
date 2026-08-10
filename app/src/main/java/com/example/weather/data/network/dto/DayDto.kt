package com.example.weather.data.network.dto

import kotlinx.serialization.SerialName

data class DayDto(
    @SerialName("date_epoch") val date: Long,
    @SerialName("day") val dayWeatherDto: DayWeatherDto
)
