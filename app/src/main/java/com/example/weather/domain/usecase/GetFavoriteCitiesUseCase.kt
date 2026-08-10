package com.example.weather.domain.usecase

import com.example.weather.domain.repository.FavouriteRepository
import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetFavouriteCitiesUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    operator fun invoke() = repository.favouriteCities
}