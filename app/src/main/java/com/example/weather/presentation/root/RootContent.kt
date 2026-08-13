package com.example.weather.presentation.root


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.example.weather.presentation.details.DetailsContent
import com.example.weather.presentation.favourite.FavouriteContent
import com.example.weather.presentation.search.SearchContent
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.weatherBackground

@Composable
fun RootContent(component: RootComponent) {
    WeatherTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weatherBackground()
        ) {
            Children(
                stack = component.stack
            ) {
                when (val instance = it.instance) {
                    is RootComponent.Child.Details -> {
                        DetailsContent(component = instance.component)
                    }

                    is RootComponent.Child.Favourite -> {
                        FavouriteContent(component = instance.component)
                    }

                    is RootComponent.Child.Search -> {
                        SearchContent(component = instance.component)
                    }
                }
            }
        }
    }
}