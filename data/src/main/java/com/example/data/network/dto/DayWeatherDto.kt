package com.example.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayWeatherDto(
    @SerialName("avgtemp_c") val avgTempC: Float,
    @SerialName("condition") val conditionDto: ConditionDto
)