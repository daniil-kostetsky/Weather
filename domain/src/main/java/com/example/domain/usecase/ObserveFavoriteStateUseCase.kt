package com.example.domain.usecase


import com.example.domain.repository.FavouriteRepository
import javax.inject.Inject

class ObserveFavouriteStateUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    operator fun invoke(cityId: Int) = repository.observeIsFavourite(cityId)
}