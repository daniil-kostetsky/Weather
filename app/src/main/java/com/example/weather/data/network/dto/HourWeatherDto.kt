package com.example.weather.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourWeatherDto(
    @SerialName("temp_c") val tempC: Float,
    @SerialName("time_epoch") val time: Long,
    @SerialName("condition") val conditionDto: ConditionDto
)