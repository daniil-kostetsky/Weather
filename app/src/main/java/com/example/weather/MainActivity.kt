package com.example.weather


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.defaultComponentContext
import com.example.weather.presentation.root.DefaultRootComponent
import com.example.weather.presentation.root.RootContent
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApp).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContent {
            RootContent(component = rootComponentFactory.create(defaultComponentContext()))
        }
    }
}
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