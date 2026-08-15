package com.example.data.repository

import com.example.data.mapper.toEntities
import com.example.data.network.api.ApiService
import com.example.domain.entity.City
import com.example.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {
    override suspend fun search(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }
}