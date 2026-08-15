package com.example.data.mapper

import com.example.data.network.dto.CityDto
import com.example.domain.entity.City

fun CityDto.toEntity(): City = City(id, name, country)

fun List<CityDto>.toEntities(): List<City> = map { it.toEntity() }