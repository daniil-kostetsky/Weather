package com.example.domain.entity

data class ForecastWeather(
    val currentWeather: CurrentWeather,
    val dayWeather: List<DayWeather>,
    val hourWeather: List<HourWeather>
)