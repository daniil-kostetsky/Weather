package com.example.weather.domain.entity

import java.util.Calendar

data class CurrentWeather(
    val tempC: Float,
    val conditionText: String,
    val conditionUrl: String,
    val date: Calendar
)