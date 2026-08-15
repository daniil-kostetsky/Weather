package com.example.weather.ui.theme

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object WeatherPalette {
    val gradientColors = listOf(
        Color(0xFF0D47A1),
        Color(0xFF1565C0),
        Color(0xFF1E88E5),
        Color(0xFF64B5F6),
        Color(0xFFE3F2FD)
    )
}

fun Modifier.weatherBackground(): Modifier = background(
    Brush.verticalGradient(WeatherPalette.gradientColors)
)