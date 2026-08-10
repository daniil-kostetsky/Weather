package com.example.weather.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayWeatherDto(
    @SerialName("avgtemp_c") val tempC: Float,
    @SerialName("condition") val conditionDto: ConditionDto
)