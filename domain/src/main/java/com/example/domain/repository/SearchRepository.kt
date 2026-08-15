package com.example.domain.repository

import com.example.domain.entity.City

interface SearchRepository {
    suspend fun search(query: String): List<City>
}