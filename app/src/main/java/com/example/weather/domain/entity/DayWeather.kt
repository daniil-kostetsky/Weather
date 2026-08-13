package com.example.weather.domain.entity

import java.util.Calendar

data class DayWeather(
    val avgTempC: Float,
    val avgConditionText: String,
    val avgConditionUrl: String,
    val date: Calendar
)