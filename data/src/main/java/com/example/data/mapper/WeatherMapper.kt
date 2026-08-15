package com.example.data.mapper

import com.example.data.network.dto.WeatherCurrentDto
import com.example.data.network.dto.WeatherDto
import com.example.data.network.dto.WeatherForecastDto
import com.example.domain.entity.CurrentWeather
import com.example.domain.entity.DayWeather
import com.example.domain.entity.ForecastWeather
import com.example.domain.entity.HourWeather
import java.util.Calendar
import java.util.Date

fun WeatherCurrentDto.toEntity(): CurrentWeather = current.toEntity()

fun WeatherDto.toEntity(): CurrentWeather = CurrentWeather(
    tempC = tempC,
    conditionText = conditionDto.text,
    conditionUrl = conditionDto.iconUrl.correctImageUrl(),
    date = date.toCalendar()
)

fun WeatherForecastDto.toEntity() = ForecastWeather(
    currentWeather = current.toEntity(),
    dayWeather = forecastDto.forecastDay.drop(1).map { dayDto ->
        val dayWeatherDto = dayDto.dayWeatherDto
        DayWeather(
            avgTempC = dayWeatherDto.avgTempC,
            avgConditionText = dayWeatherDto.conditionDto.text,
            avgConditionUrl = dayWeatherDto.conditionDto.iconUrl.correctImageUrl(),
            date = dayDto.date.toCalendar()
        )
    },
    hourWeather = forecastDto.forecastDay.first()
        .let { dayDto -> // по часам нужен только текущий день
            dayDto.hourWeatherDto.map { hourWeatherDto ->
                HourWeather(
                    tempC = hourWeatherDto.tempC,
                    conditionText = hourWeatherDto.conditionDto.text,
                    conditionUrl = hourWeatherDto.conditionDto.iconUrl.correctImageUrl(),
                    date = hourWeatherDto.time.toCalendar(),
                )
            }
        }
)



private fun Long.toCalendar() = Calendar.getInstance().apply {
    time = Date(this@toCalendar * 1000)
}

private fun String.correctImageUrl() = "https:$this".replace(
    oldValue = "64x64",
    newValue = "128x128"
)