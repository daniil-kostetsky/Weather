package com.example.weather.data.network.dto

import kotlinx.serialization.SerialName

data class WeatherDto(
    @SerialName("last_updated_epoch") val date: Long,
    @SerialName("temp_c") val tempC: Float,
    @SerialName("condition") val conditionDto: ConditionDto
)