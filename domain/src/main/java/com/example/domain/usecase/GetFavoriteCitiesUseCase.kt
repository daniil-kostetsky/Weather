package com.example.domain.usecase

import com.example.domain.repository.FavouriteRepository
import javax.inject.Inject

class GetFavouriteCitiesUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    operator fun invoke() = repository.favouriteCities
}