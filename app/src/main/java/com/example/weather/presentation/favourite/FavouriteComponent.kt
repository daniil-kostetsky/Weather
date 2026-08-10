package com.example.weather.presentation.favourite


import com.example.weather.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavouriteComponent {

    val model: StateFlow<FavouriteStore.State>

    fun onClickSearch()

    fun onCLickAddFavourite()

    fun onCityItemClick(city: City)
}